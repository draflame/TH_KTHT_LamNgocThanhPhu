const express = require('express');
const redis = require('redis');
const cors = require('cors');
const { v4: uuidv4 } = require('uuid');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 8082;

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

// Routes

// Get cart for user
app.get('/api/cart/:userId', async (req, res) => {
  try {
    const { userId } = req.params;
    const cart = await client.get(`cart:${userId}`);

    if (!cart) {
      return res.json({
        success: true,
        data: { userId, items: [], total: 0 },
        timestamp: new Date().toISOString()
      });
    }

    res.json({
      success: true,
      data: JSON.parse(cart),
      timestamp: new Date().toISOString()
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Error fetching cart',
      error: error.message
    });
  }
});

// Add item to cart (FAST - no DB wait)
app.post('/api/cart/:userId/add', async (req, res) => {
  try {
    const { userId } = req.params;
    const { productId, productName, price, quantity = 1 } = req.body;

    if (!productId || !productName || !price) {
      return res.status(400).json({
        success: false,
        message: 'Missing required fields'
      });
    }

    // Get existing cart
    let cart = await client.get(`cart:${userId}`);
    cart = cart ? JSON.parse(cart) : { userId, items: [], total: 0 };

    // Check if item exists
    const existingItem = cart.items.find(item => item.productId === productId);

    if (existingItem) {
      existingItem.quantity += quantity;
    } else {
      cart.items.push({
        cartItemId: uuidv4(),
        productId,
        productName,
        price,
        quantity
      });
    }

    // Recalculate total
    cart.total = cart.items.reduce((sum, item) => sum + (item.price * item.quantity), 0);

    // FAST: Save to Redis without waiting for any response
    await client.set(`cart:${userId}`, JSON.stringify(cart), {
      EX: 86400 // 24 hours expiry
    });

    // Return immediately
    res.json({
      success: true,
      message: 'Item added to cart successfully',
      data: cart,
      timestamp: new Date().toISOString()
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Error adding item to cart',
      error: error.message
    });
  }
});

// Remove item from cart
app.post('/api/cart/:userId/remove/:cartItemId', async (req, res) => {
  try {
    const { userId, cartItemId } = req.params;
    
    let cart = await client.get(`cart:${userId}`);
    if (!cart) {
      return res.status(404).json({
        success: false,
        message: 'Cart not found'
      });
    }

    cart = JSON.parse(cart);
    cart.items = cart.items.filter(item => item.cartItemId !== cartItemId);
    cart.total = cart.items.reduce((sum, item) => sum + (item.price * item.quantity), 0);

    await client.set(`cart:${userId}`, JSON.stringify(cart), {
      EX: 86400
    });

    res.json({
      success: true,
      message: 'Item removed from cart',
      data: cart,
      timestamp: new Date().toISOString()
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Error removing item from cart',
      error: error.message
    });
  }
});

// Clear cart
app.post('/api/cart/:userId/clear', async (req, res) => {
  try {
    const { userId } = req.params;
    await client.del(`cart:${userId}`);

    res.json({
      success: true,
      message: 'Cart cleared',
      data: { userId, items: [], total: 0 },
      timestamp: new Date().toISOString()
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Error clearing cart',
      error: error.message
    });
  }
});

// Health check
app.get('/health', (req, res) => {
  res.json({ status: 'Cart Service is running' });
});

// Start server
app.listen(PORT, () => {
  console.log(`Cart Service listening on port ${PORT}`);
});

process.on('SIGINT', async () => {
  console.log('Shutting down Cart Service');
  await client.quit();
  process.exit(0);
});
