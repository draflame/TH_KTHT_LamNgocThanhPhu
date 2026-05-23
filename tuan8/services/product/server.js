const express = require('express');
const redis = require('redis');
const cors = require('cors');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 8081;

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

// Initialize sample products in Redis (called once)
async function initializeProducts() {
  const products = [
    { id: 'P001', name: 'Laptop Dell XPS 13', price: 1299.99, category: 'Electronics' },
    { id: 'P002', name: 'iPhone 15 Pro', price: 999.99, category: 'Electronics' },
    { id: 'P003', name: 'Samsung Galaxy Watch', price: 349.99, category: 'Wearables' },
    { id: 'P004', name: 'AirPods Pro', price: 249.99, category: 'Audio' },
    { id: 'P005', name: 'Sony WH-1000XM5 Headphones', price: 399.99, category: 'Audio' }
  ];

  for (const product of products) {
    try {
      await client.set(`product:${product.id}`, JSON.stringify(product), {
        EX: 86400 * 30 // 30 days expiry
      });
    } catch (error) {
      console.error(`Error saving product ${product.id}:`, error);
    }
  }
  console.log('Products initialized in Redis');
}

// Routes

// Get all products
app.get('/api/products', async (req, res) => {
  try {
    const productIds = ['P001', 'P002', 'P003', 'P004', 'P005'];
    const products = [];

    for (const id of productIds) {
      const product = await client.get(`product:${id}`);
      if (product) {
        products.push(JSON.parse(product));
      }
    }

    res.json({
      success: true,
      data: products,
      timestamp: new Date().toISOString()
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Error fetching products',
      error: error.message
    });
  }
});

// Get single product
app.get('/api/products/:productId', async (req, res) => {
  try {
    const { productId } = req.params;
    const product = await client.get(`product:${productId}`);

    if (!product) {
      return res.status(404).json({
        success: false,
        message: 'Product not found'
      });
    }

    res.json({
      success: true,
      data: JSON.parse(product),
      timestamp: new Date().toISOString()
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Error fetching product',
      error: error.message
    });
  }
});

// Create/Update product
app.post('/api/products', async (req, res) => {
  try {
    const { id, name, price, category } = req.body;

    if (!id || !name || !price) {
      return res.status(400).json({
        success: false,
        message: 'Missing required fields'
      });
    }

    const product = { id, name, price, category };
    await client.set(`product:${id}`, JSON.stringify(product), {
      EX: 86400 * 30
    });

    res.status(201).json({
      success: true,
      message: 'Product saved successfully',
      data: product,
      timestamp: new Date().toISOString()
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Error saving product',
      error: error.message
    });
  }
});

// Health check
app.get('/health', (req, res) => {
  res.json({ status: 'Product Service is running' });
});

// Start server
app.listen(PORT, async () => {
  console.log(`Product Service listening on port ${PORT}`);
  await initializeProducts();
});

process.on('SIGINT', async () => {
  console.log('Shutting down Product Service');
  await client.quit();
  process.exit(0);
});
