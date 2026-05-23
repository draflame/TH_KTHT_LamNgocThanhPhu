const express = require('express');
const redis = require('redis');
const cors = require('cors');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 8084;

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

// Initialize inventory for products
async function initializeInventory() {
  const inventory = {
    'P001': { productId: 'P001', productName: 'Laptop Dell XPS 13', stock: 50 },
    'P002': { productId: 'P002', productName: 'iPhone 15 Pro', stock: 100 },
    'P003': { productId: 'P003', productName: 'Samsung Galaxy Watch', stock: 75 },
    'P004': { productId: 'P004', productName: 'AirPods Pro', stock: 200 },
    'P005': { productId: 'P005', productName: 'Sony WH-1000XM5 Headphones', stock: 60 }
  };

  for (const [productId, item] of Object.entries(inventory)) {
    try {
      await client.set(`inventory:${productId}`, JSON.stringify(item), {
        EX: 86400 * 30 // 30 days expiry
      });
    } catch (error) {
      console.error(`Error saving inventory for ${productId}:`, error);
    }
  }
  console.log('Inventory initialized in Redis');
}

// Routes

// Get inventory for product
app.get('/api/inventory/:productId', async (req, res) => {
  try {
    const { productId } = req.params;
    const inventory = await client.get(`inventory:${productId}`);

    if (!inventory) {
      return res.status(404).json({
        success: false,
        message: 'Product inventory not found'
      });
    }

    res.json({
      success: true,
      data: JSON.parse(inventory),
      timestamp: new Date().toISOString()
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Error fetching inventory',
      error: error.message
    });
  }
});

// Get all inventory
app.get('/api/inventory', async (req, res) => {
  try {
    const productIds = ['P001', 'P002', 'P003', 'P004', 'P005'];
    const inventories = [];

    for (const id of productIds) {
      const inventory = await client.get(`inventory:${id}`);
      if (inventory) {
        inventories.push(JSON.parse(inventory));
      }
    }

    res.json({
      success: true,
      data: inventories,
      timestamp: new Date().toISOString()
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Error fetching inventory',
      error: error.message
    });
  }
});

// Reduce stock - FAST operation (IMMEDIATE RETURN - NO WAIT)
app.post('/api/inventory/:productId/reduce', async (req, res) => {
  try {
    const { productId } = req.params;
    const { quantity } = req.body;

    if (!quantity || quantity <= 0) {
      return res.status(400).json({
        success: false,
        message: 'Invalid quantity'
      });
    }

    let inventory = await client.get(`inventory:${productId}`);
    if (!inventory) {
      return res.status(404).json({
        success: false,
        message: 'Product inventory not found'
      });
    }

    inventory = JSON.parse(inventory);

    if (inventory.stock < quantity) {
      return res.status(400).json({
        success: false,
        message: 'Insufficient stock',
        currentStock: inventory.stock,
        requestedQuantity: quantity
      });
    }

    // REDUCE STOCK IMMEDIATELY (no DB wait)
    inventory.stock -= quantity;

    // Save to Redis asynchronously WITHOUT waiting
    client.set(`inventory:${productId}`, JSON.stringify(inventory), {
      EX: 86400 * 30
    }).catch(err => console.error('Error updating inventory:', err));

    // Return result IMMEDIATELY (ASYNC SAVE)
    res.json({
      success: true,
      message: 'Stock reduced successfully',
      data: {
        productId,
        previousStock: inventory.stock + quantity,
        newStock: inventory.stock,
        quantityReduced: quantity
      },
      timestamp: new Date().toISOString()
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Error reducing stock',
      error: error.message
    });
  }
});

// Bulk check stock availability
app.post('/api/inventory/check-availability', async (req, res) => {
  try {
    const { items } = req.body; // Array of {productId, quantity}

    if (!Array.isArray(items)) {
      return res.status(400).json({
        success: false,
        message: 'Items must be an array'
      });
    }

    const availability = [];
    let allAvailable = true;

    for (const item of items) {
      const inventory = await client.get(`inventory:${item.productId}`);
      
      if (!inventory) {
        availability.push({
          productId: item.productId,
          available: false,
          reason: 'Product not found'
        });
        allAvailable = false;
      } else {
        const inv = JSON.parse(inventory);
        const isAvailable = inv.stock >= item.quantity;
        
        availability.push({
          productId: item.productId,
          available: isAvailable,
          currentStock: inv.stock,
          requestedQuantity: item.quantity
        });

        if (!isAvailable) {
          allAvailable = false;
        }
      }
    }

    res.json({
      success: true,
      allAvailable,
      data: availability,
      timestamp: new Date().toISOString()
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Error checking availability',
      error: error.message
    });
  }
});

// Health check
app.get('/health', (req, res) => {
  res.json({ status: 'Inventory Service is running' });
});

// Start server
app.listen(PORT, async () => {
  console.log(`Inventory Service listening on port ${PORT}`);
  await initializeInventory();
});

process.on('SIGINT', async () => {
  console.log('Shutting down Inventory Service');
  await client.quit();
  process.exit(0);
});
