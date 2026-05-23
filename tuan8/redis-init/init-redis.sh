#!/bin/bash

# Redis Data Initialization Script for Linux/Mac
# This script imports sample data into Redis

REDIS_HOST=${REDIS_HOST:-localhost}
REDIS_PORT=${REDIS_PORT:-6379}
REDIS_PASSWORD=${REDIS_PASSWORD:-}
REDIS_DB=${REDIS_DB:-0}

# Color codes
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${YELLOW}🔴 Redis Data Initialization Script${NC}"
echo "Host: $REDIS_HOST:$REDIS_PORT"
echo "DB: $REDIS_DB"
echo ""

# Build redis-cli command
REDIS_CMD="redis-cli -h $REDIS_HOST -p $REDIS_PORT -n $REDIS_DB"
if [ -n "$REDIS_PASSWORD" ]; then
  REDIS_CMD="$REDIS_CMD -a $REDIS_PASSWORD"
fi

# Check Redis connection
echo -e "${YELLOW}Testing Redis connection...${NC}"
if ! $REDIS_CMD ping > /dev/null 2>&1; then
  echo -e "${RED}✗ Cannot connect to Redis at $REDIS_HOST:$REDIS_PORT${NC}"
  exit 1
fi
echo -e "${GREEN}✓ Connected to Redis${NC}"
echo ""

# Function to set key-value pair
set_key() {
  local key=$1
  local value=$2
  local ttl=$3
  
  if [ -z "$ttl" ]; then
    $REDIS_CMD SET "$key" "$value" > /dev/null
  else
    $REDIS_CMD SET "$key" "$value" EX "$ttl" > /dev/null
  fi
}

# ===== PRODUCTS (30 days TTL) =====
echo -e "${YELLOW}Importing Products...${NC}"

set_key "product:P001" '{"id":"P001","name":"Laptop Dell XPS 13","price":1299.99,"category":"Electronics"}' 2592000
echo -e "${GREEN}✓ P001 - Laptop Dell XPS 13 (1299.99)${NC}"

set_key "product:P002" '{"id":"P002","name":"iPhone 15 Pro","price":999.99,"category":"Electronics"}' 2592000
echo -e "${GREEN}✓ P002 - iPhone 15 Pro (999.99)${NC}"

set_key "product:P003" '{"id":"P003","name":"Samsung Galaxy Watch","price":349.99,"category":"Wearables"}' 2592000
echo -e "${GREEN}✓ P003 - Samsung Galaxy Watch (349.99)${NC}"

set_key "product:P004" '{"id":"P004","name":"AirPods Pro","price":249.99,"category":"Audio"}' 2592000
echo -e "${GREEN}✓ P004 - AirPods Pro (249.99)${NC}"

set_key "product:P005" '{"id":"P005","name":"Sony WH-1000XM5 Headphones","price":399.99,"category":"Audio"}' 2592000
echo -e "${GREEN}✓ P005 - Sony WH-1000XM5 Headphones (399.99)${NC}"

echo ""

# ===== INVENTORY (30 days TTL) =====
echo -e "${YELLOW}Importing Inventory...${NC}"

set_key "inventory:P001" '{"productId":"P001","productName":"Laptop Dell XPS 13","stock":50}' 2592000
echo -e "${GREEN}✓ P001 Inventory: 50 units${NC}"

set_key "inventory:P002" '{"productId":"P002","productName":"iPhone 15 Pro","stock":100}' 2592000
echo -e "${GREEN}✓ P002 Inventory: 100 units${NC}"

set_key "inventory:P003" '{"productId":"P003","productName":"Samsung Galaxy Watch","stock":75}' 2592000
echo -e "${GREEN}✓ P003 Inventory: 75 units${NC}"

set_key "inventory:P004" '{"productId":"P004","productName":"AirPods Pro","stock":200}' 2592000
echo -e "${GREEN}✓ P004 Inventory: 200 units${NC}"

set_key "inventory:P005" '{"productId":"P005","productName":"Sony WH-1000XM5 Headphones","stock":60}' 2592000
echo -e "${GREEN}✓ P005 Inventory: 60 units${NC}"

echo ""

# Verify data
echo -e "${YELLOW}Verifying imported data...${NC}"
PRODUCT_COUNT=$($REDIS_CMD KEYS "product:*" | wc -l)
INVENTORY_COUNT=$($REDIS_CMD KEYS "inventory:*" | wc -l)

echo -e "${GREEN}✓ Products in Redis: $PRODUCT_COUNT${NC}"
echo -e "${GREEN}✓ Inventory records in Redis: $INVENTORY_COUNT${NC}"

echo ""
echo -e "${GREEN}✓ All data imported successfully!${NC}"
echo ""
echo -e "${YELLOW}Sample Redis keys:${NC}"
$REDIS_CMD KEYS "*" | head -10

echo ""
echo -e "${YELLOW}Sample product data:${NC}"
$REDIS_CMD GET "product:P001"

echo ""
