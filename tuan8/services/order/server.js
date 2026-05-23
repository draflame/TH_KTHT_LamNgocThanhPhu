const express = require('express');
const redis = require('redis');
const cors = require('cors');
const axios = require('axios');
const { v4: uuidv4 } = require('uuid');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 8083;

// Middleware
app.use(express.json());
app.use(cors());

// Redis client
const client = redis.createClient({
  url: `redis://${process.env.REDIS_HOST || 'localhost'}:${process.env.REDIS_PORT || 6379}`
});

client.on('error', (err) => console.log('Redis Client Error', err));
client.on('connect', () => console.log('Connected to Redis'));

// Connect to Redis
client.connect();

// Service URLs
const INVENTORY_SERVICE_URL = process.env.INVENTORY_SERVICE_URL || 'http://localhost:8084';

// Routes

// Get order
app.get('/api/orders/:orderId', async (req, res) => {
  try {
    const { orderId } = req.params;
    const order = await client.get(`order:${orderId}`);

    if (!order) {
      return res.status(404).json({
        success: false,
        message: 'Order not found'
      });
    }

    res.json({
      success: true,
      data: JSON.parse(order),
      timestamp: new Date().toISOString()
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Error fetching order',
      error: error.message
    });
  }
});

// Get user orders
app.get('/api/orders/user/:userId', async (req, res) => {
  try {
    const { userId } = req.params;
    const ordersKey = await client.keys(`order:*`);
    const orders = [];

    for (const key of ordersKey) {
      const order = await client.get(key);
      const parsedOrder = JSON.parse(order);
      if (parsedOrder.userId === userId) {
        orders.push(parsedOrder);
      }
    }

    res.json({
      success: true,
      data: orders,
      timestamp: new Date().toISOString()
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Error fetching orders',
      error: error.message
    });
  }
});

// CHECKOUT - MAIN WORKFLOW: Get cart → Check inventory → Reduce stock → Return IMMEDIATELY
app.post('/api/checkout', async (req, res) => {
  try {
    const { userId, cartData } = req.body;

    if (!userId || !cartData || !Array.isArray(cartData.items) || cartData.items.length === 0) {
      return res.status(400).json({
        success: false,
        message: 'Invalid checkout data'
      });
    }

    const orderId = uuidv4();
    const orderTimestamp = new Date().toISOString();

    // 1. Create order object IMMEDIATELY
    const order = {
      orderId,
      userId,
      items: cartData.items,
      total: cartData.total,
      status: 'PROCESSING',
      createdAt: orderTimestamp,
      updatedAt: orderTimestamp
    };

    // 2. Save order to Redis IMMEDIATELY (fire and forget style for speed)
    client.set(`order:${orderId}`, JSON.stringify(order), {
      EX: 86400 * 30 // 30 days
    }).catch(err => console.error('Error saving order:', err));

    // 3. Reduce inventory IMMEDIATELY FOR EACH ITEM (async, no wait)
    // This happens in parallel without blocking the response
    cartData.items.forEach(item => {
      axios.post(`${INVENTORY_SERVICE_URL}/api/inventory/${item.productId}/reduce`, {
        quantity: item.quantity
      }).then(response => {
        console.log(`Stock reduced for ${item.productId}:`, response.data);
      }).catch(error => {
        console.error(`Error reducing stock for ${item.productId}:`, error.message);
      });
    });

    // 4. RETURN IMMEDIATELY - DO NOT WAIT FOR INVENTORY OPERATIONS
    res.status(201).json({
      success: true,
      message: 'Order created successfully - Stock reduction in progress',
      data: {
        orderId,
        userId,
        itemCount: cartData.items.length,
        total: cartData.total,
        status: 'PROCESSING',
        createdAt: orderTimestamp
      },
      timestamp: new Date().toISOString()
    });

  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Error processing checkout',
      error: error.message
    });
  }
});

// CHECKOUT WITH VALIDATION - Check inventory first, then process
app.post('/api/checkout/validated', async (req, res) => {
  try {
    const { userId, cartData } = req.body;

    if (!userId || !cartData || !Array.isArray(cartData.items) || cartData.items.length === 0) {
      return res.status(400).json({
        success: false,
        message: 'Invalid checkout data'
      });
    }

    // 1. Check inventory availability for all items
    const checkRequest = {
      items: cartData.items.map(item => ({
        productId: item.productId,
        quantity: item.quantity
      }))
    };

    let inventoryCheck;
    try {
      const response = await axios.post(
        `${INVENTORY_SERVICE_URL}/api/inventory/check-availability`,
        checkRequest
      );
      inventoryCheck = response.data;
    } catch (error) {
      return res.status(503).json({
        success: false,
        message: 'Cannot reach inventory service',
        error: error.message
      });
    }

    // 2. If not all items available, reject order
    if (!inventoryCheck.success || !inventoryCheck.allAvailable) {
      return res.status(400).json({
        success: false,
        message: 'Insufficient stock for some items',
        data: inventoryCheck.data
      });
    }

    // 3. Create order
    const orderId = uuidv4();
    const orderTimestamp = new Date().toISOString();

    const order = {
      orderId,
      userId,
      items: cartData.items,
      total: cartData.total,
      status: 'CONFIRMED',
      createdAt: orderTimestamp,
      updatedAt: orderTimestamp
    };

    // 4. Save order
    client.set(`order:${orderId}`, JSON.stringify(order), {
      EX: 86400 * 30
    }).catch(err => console.error('Error saving order:', err));

    // 5. Reduce stock ASYNC (fire and forget)
    cartData.items.forEach(item => {
      axios.post(`${INVENTORY_SERVICE_URL}/api/inventory/${item.productId}/reduce`, {
        quantity: item.quantity
      }).catch(error => {
        console.error(`Error reducing stock for ${item.productId}:`, error.message);
      });
    });

    // 6. Return immediately
    res.status(201).json({
      success: true,
      message: 'Order confirmed successfully',
      data: {
        orderId,
        userId,
        itemCount: cartData.items.length,
        total: cartData.total,
        status: 'CONFIRMED',
        createdAt: orderTimestamp
      },
      timestamp: new Date().toISOString()
    });

  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Error processing checkout',
      error: error.message
    });
  }
});

// Health check
app.get('/health', (req, res) => {
  res.json({ status: 'Order Service is running' });
});

// Start server
app.listen(PORT, () => {
  console.log(`Order Service listening on port ${PORT}`);
});

process.on('SIGINT', async () => {
  console.log('Shutting down Order Service');
  await client.quit();
  process.exit(0);
});
