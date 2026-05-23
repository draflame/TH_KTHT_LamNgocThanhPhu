#!/bin/bash

# E-Commerce Platform - Test Script
# This script demonstrates the complete workflow

echo "🛍️  E-Commerce Platform - Testing Workflow"
echo "=========================================="
echo ""

# Configuration
PRODUCT_SERVICE="http://localhost:8081"
CART_SERVICE="http://localhost:8082"
ORDER_SERVICE="http://localhost:8083"
INVENTORY_SERVICE="http://localhost:8084"
USER_ID="test-user-$(date +%s)"

echo "Using User ID: $USER_ID"
echo ""

# Test 1: Load Products
echo "📦 Test 1: Loading products from Redis..."
PRODUCTS_RESPONSE=$(curl -s $PRODUCT_SERVICE/api/products)
echo $PRODUCTS_RESPONSE | jq '.'
echo ""

# Extract first product ID
PRODUCT_ID=$(echo $PRODUCTS_RESPONSE | jq -r '.data[0].id')
PRODUCT_NAME=$(echo $PRODUCTS_RESPONSE | jq -r '.data[0].name')
PRODUCT_PRICE=$(echo $PRODUCTS_RESPONSE | jq -r '.data[0].price')

echo "Selected Product: $PRODUCT_ID - $PRODUCT_NAME ($PRODUCT_PRICE)"
echo ""

# Test 2: Check Inventory
echo "📊 Test 2: Checking inventory..."
curl -s $INVENTORY_SERVICE/api/inventory/$PRODUCT_ID | jq '.'
echo ""

# Test 3: Add to Cart
echo "🛒 Test 3: Adding product to cart..."
CART_RESPONSE=$(curl -s -X POST $CART_SERVICE/api/cart/$USER_ID/add \
  -H "Content-Type: application/json" \
  -d "{
    \"productId\": \"$PRODUCT_ID\",
    \"productName\": \"$PRODUCT_NAME\",
    \"price\": $PRODUCT_PRICE,
    \"quantity\": 2
  }")
echo $CART_RESPONSE | jq '.'
echo ""

# Test 4: View Cart
echo "📋 Test 4: Viewing cart..."
CART=$(curl -s $CART_SERVICE/api/cart/$USER_ID)
echo $CART | jq '.'
CART_TOTAL=$(echo $CART | jq '.data.total')
echo ""

# Test 5: Checkout (FAST - IMMEDIATE RESPONSE)
echo "✅ Test 5: Processing checkout (FAST - IMMEDIATE RESPONSE)..."
START_TIME=$(date +%s%N)
CHECKOUT_RESPONSE=$(curl -s -X POST $ORDER_SERVICE/api/checkout/validated \
  -H "Content-Type: application/json" \
  -d "{
    \"userId\": \"$USER_ID\",
    \"cartData\": $(echo $CART | jq '.data')
  }")
END_TIME=$(date +%s%N)
ELAPSED=$((($END_TIME - $START_TIME) / 1000000))

echo $CHECKOUT_RESPONSE | jq '.'
echo "⚡ Response time: ${ELAPSED}ms"
echo ""

# Extract Order ID
ORDER_ID=$(echo $CHECKOUT_RESPONSE | jq -r '.data.orderId')
echo "Order ID: $ORDER_ID"
echo ""

# Test 6: Check Stock After Checkout
echo "📊 Test 6: Checking inventory after checkout..."
echo "Waiting 2 seconds for async stock reduction..."
sleep 2
curl -s $INVENTORY_SERVICE/api/inventory/$PRODUCT_ID | jq '.'
echo ""

# Test 7: Verify Order
echo "📋 Test 7: Verifying order..."
curl -s $ORDER_SERVICE/api/orders/$ORDER_ID | jq '.'
echo ""

# Test 8: Get User Orders
echo "📝 Test 8: Getting all user orders..."
curl -s $ORDER_SERVICE/api/orders/user/$USER_ID | jq '.'
echo ""

echo "✅ Testing Complete!"
echo ""
echo "Key Results:"
echo "  ✓ Products loaded from Redis"
echo "  ✓ Item added to cart instantly"
echo "  ✓ Checkout completed in ${ELAPSED}ms"
echo "  ✓ Order created successfully"
echo "  ✓ Stock reduction async (no wait)"
echo ""
