#!/usr/bin/env node

/**
 * Redis Data Initialization Script (Node.js)
 * Imports sample data from JSON file into Redis
 * Works on all platforms: Windows, Linux, Mac
 */

const redis = require('redis');
const fs = require('fs');
const path = require('path');

// Configuration
const REDIS_HOST = process.env.REDIS_HOST || 'localhost';
const REDIS_PORT = process.env.REDIS_PORT || 6379;
const REDIS_DB = process.env.REDIS_DB || 0;
const DATA_FILE = process.env.DATA_FILE || path.join(__dirname, 'data.json');

// TTLs (in seconds)
const PRODUCT_TTL = 30 * 24 * 60 * 60; // 30 days
const INVENTORY_TTL = 30 * 24 * 60 * 60; // 30 days

async function initializeRedis() {
  try {
    // Create Redis client
    const client = redis.createClient({
      host: REDIS_HOST,
      port: REDIS_PORT,
      db: REDIS_DB,
      socket: {
        reconnectStrategy: (retries) => {
          if (retries > 3) {
            console.error('❌ Redis connection failed after 3 retries');
            return new Error('Max retries exceeded');
          }
          return retries * 100;
        }
      }
    });

    // Handle connection events
    client.on('error', (err) => {
      console.error('❌ Redis Client Error:', err);
      process.exit(1);
    });

    client.on('connect', () => {
      console.log('✓ Connected to Redis at', `${REDIS_HOST}:${REDIS_PORT}`);
    });

    // Connect to Redis
    await client.connect();

    // Test connection
    const pong = await client.ping();
    if (pong === 'PONG') {
      console.log('✓ Redis is responding to PING');
    }

    console.log('');
    console.log('🔴 Redis Data Initialization');
    console.log('================================');
    console.log('');

    // Read data file
    console.log('📂 Reading data file:', DATA_FILE);
    if (!fs.existsSync(DATA_FILE)) {
      throw new Error(`Data file not found: ${DATA_FILE}`);
    }

    const data = JSON.parse(fs.readFileSync(DATA_FILE, 'utf8'));
    console.log('✓ Data file loaded');
    console.log('');

    // Import products
    console.log('📦 Importing Products...');
    if (data.products && Array.isArray(data.products)) {
      for (const product of data.products) {
        const key = `product:${product.id}`;
        await client.setEx(key, PRODUCT_TTL, JSON.stringify(product));
        console.log(`  ✓ ${product.id} - ${product.name} ($${product.price})`);
      }
      console.log(`✓ Imported ${data.products.length} products`);
    }
    console.log('');

    // Import inventory
    console.log('📊 Importing Inventory...');
    if (data.inventory && Array.isArray(data.inventory)) {
      for (const item of data.inventory) {
        const key = `inventory:${item.productId}`;
        await client.setEx(key, INVENTORY_TTL, JSON.stringify(item));
        console.log(`  ✓ ${item.productId} - ${item.productName}: ${item.stock} units`);
      }
      console.log(`✓ Imported ${data.inventory.length} inventory records`);
    }
    console.log('');

    // Verify data
    console.log('🔍 Verifying data...');
    const keys = await client.keys('*');
    console.log(`✓ Total keys in Redis: ${keys.length}`);
    console.log('');

    // Show sample
    console.log('📋 Sample Redis Keys:');
    keys.slice(0, 10).forEach(key => console.log(`  - ${key}`));
    console.log('');

    // Show sample data
    console.log('📄 Sample Product Data:');
    const sampleProduct = await client.get('product:P001');
    if (sampleProduct) {
      console.log('  ' + sampleProduct);
    }
    console.log('');

    console.log('================================');
    console.log('✅ All data imported successfully!');
    console.log('================================');
    console.log('');

    // Close connection
    await client.quit();
    process.exit(0);

  } catch (error) {
    console.error('❌ Error:', error.message);
    process.exit(1);
  }
}

// Run initialization
initializeRedis();
