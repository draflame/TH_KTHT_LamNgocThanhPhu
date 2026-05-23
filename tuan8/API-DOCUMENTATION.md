# 📚 API Documentation

## Base URLs

- **Product Service:** `http://localhost:8081`
- **Cart Service:** `http://localhost:8082`
- **Order Service:** `http://localhost:8083`
- **Inventory Service:** `http://localhost:8084`

---

## Product Service (Port 8081)

### Get All Products
Returns list of all available products from Redis cache.

**Endpoint:** `GET /api/products`

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": "P001",
      "name": "Laptop Dell XPS 13",
      "price": 1299.99,
      "category": "Electronics"
    }
  ],
  "timestamp": "2024-01-15T10:30:45.123Z"
}
```

### Get Single Product
Retrieve specific product details.

**Endpoint:** `GET /api/products/{productId}`

**Example:** `GET /api/products/P001`

**Response:**
```json
{
  "success": true,
  "data": {
    "id": "P001",
    "name": "Laptop Dell XPS 13",
    "price": 1299.99,
    "category": "Electronics"
  },
  "timestamp": "2024-01-15T10:30:45.123Z"
}
```

### Create/Update Product
Add new product or update existing.

**Endpoint:** `POST /api/products`

**Body:**
```json
{
  "id": "P006",
  "name": "New Product",
  "price": 99.99,
  "category": "Category"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Product saved successfully",
  "data": {
    "id": "P006",
    "name": "New Product",
    "price": 99.99,
    "category": "Category"
  },
  "timestamp": "2024-01-15T10:30:45.123Z"
}
```

### Health Check
**Endpoint:** `GET /health`

---

## Cart Service (Port 8082)

### Get Cart
Retrieve user's shopping cart.

**Endpoint:** `GET /api/cart/{userId}`

**Example:** `GET /api/cart/user-123`

**Response:**
```json
{
  "success": true,
  "data": {
    "userId": "user-123",
    "items": [
      {
        "cartItemId": "uuid",
        "productId": "P001",
        "productName": "Laptop Dell XPS 13",
        "price": 1299.99,
        "quantity": 1
      }
    ],
    "total": 1299.99
  },
  "timestamp": "2024-01-15T10:30:45.123Z"
}
```

### Add Item to Cart
⚡ **FAST OPERATION** - Returns immediately without waiting.

**Endpoint:** `POST /api/cart/{userId}/add`

**Example:** `POST /api/cart/user-123/add`

**Body:**
```json
{
  "productId": "P001",
  "productName": "Laptop Dell XPS 13",
  "price": 1299.99,
  "quantity": 1
}
```

**Response:** (Immediate - ~10ms)
```json
{
  "success": true,
  "message": "Item added to cart successfully",
  "data": {
    "userId": "user-123",
    "items": [...],
    "total": 1299.99
  },
  "timestamp": "2024-01-15T10:30:45.123Z"
}
```

### Remove Item from Cart
Remove specific item from cart.

**Endpoint:** `POST /api/cart/{userId}/remove/{cartItemId}`

**Example:** `POST /api/cart/user-123/remove/uuid123`

**Response:**
```json
{
  "success": true,
  "message": "Item removed from cart",
  "data": {
    "userId": "user-123",
    "items": [],
    "total": 0
  },
  "timestamp": "2024-01-15T10:30:45.123Z"
}
```

### Clear Cart
Remove all items from cart.

**Endpoint:** `POST /api/cart/{userId}/clear`

**Example:** `POST /api/cart/user-123/clear`

**Response:**
```json
{
  "success": true,
  "message": "Cart cleared",
  "data": {
    "userId": "user-123",
    "items": [],
    "total": 0
  },
  "timestamp": "2024-01-15T10:30:45.123Z"
}
```

### Health Check
**Endpoint:** `GET /health`

---

## Inventory Service (Port 8084)

### Get All Inventory
Retrieve stock levels for all products.

**Endpoint:** `GET /api/inventory`

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "productId": "P001",
      "productName": "Laptop Dell XPS 13",
      "stock": 50
    }
  ],
  "timestamp": "2024-01-15T10:30:45.123Z"
}
```

### Get Product Inventory
Get stock level for specific product.

**Endpoint:** `GET /api/inventory/{productId}`

**Example:** `GET /api/inventory/P001`

**Response:**
```json
{
  "success": true,
  "data": {
    "productId": "P001",
    "productName": "Laptop Dell XPS 13",
    "stock": 50
  },
  "timestamp": "2024-01-15T10:30:45.123Z"
}
```

### Reduce Stock
⚡ **FAST OPERATION** - Returns immediately, stock reduction async.

**Endpoint:** `POST /api/inventory/{productId}/reduce`

**Example:** `POST /api/inventory/P001/reduce`

**Body:**
```json
{
  "quantity": 2
}
```

**Response:** (Immediate - ~30ms, stock reduction happens async)
```json
{
  "success": true,
  "message": "Stock reduced successfully",
  "data": {
    "productId": "P001",
    "previousStock": 50,
    "newStock": 48,
    "quantityReduced": 2
  },
  "timestamp": "2024-01-15T10:30:45.123Z"
}
```

### Check Availability
Check if multiple items are in stock.

**Endpoint:** `POST /api/inventory/check-availability`

**Body:**
```json
{
  "items": [
    {"productId": "P001", "quantity": 2},
    {"productId": "P002", "quantity": 1}
  ]
}
```

**Response:**
```json
{
  "success": true,
  "allAvailable": true,
  "data": [
    {
      "productId": "P001",
      "available": true,
      "currentStock": 50,
      "requestedQuantity": 2
    }
  ],
  "timestamp": "2024-01-15T10:30:45.123Z"
}
```

### Health Check
**Endpoint:** `GET /health`

---

## Order Service (Port 8083)

### Get Order
Retrieve specific order by ID.

**Endpoint:** `GET /api/orders/{orderId}`

**Response:**
```json
{
  "success": true,
  "data": {
    "orderId": "uuid",
    "userId": "user-123",
    "items": [
      {
        "cartItemId": "uuid",
        "productId": "P001",
        "productName": "Laptop Dell XPS 13",
        "price": 1299.99,
        "quantity": 1
      }
    ],
    "total": 1299.99,
    "status": "CONFIRMED",
    "createdAt": "2024-01-15T10:30:45.123Z",
    "updatedAt": "2024-01-15T10:30:45.123Z"
  },
  "timestamp": "2024-01-15T10:30:45.123Z"
}
```

### Get User Orders
Retrieve all orders for a user.

**Endpoint:** `GET /api/orders/user/{userId}`

**Example:** `GET /api/orders/user/user-123`

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "orderId": "uuid1",
      "userId": "user-123",
      "items": [...],
      "total": 1299.99,
      "status": "CONFIRMED",
      "createdAt": "2024-01-15T10:30:45.123Z"
    }
  ],
  "timestamp": "2024-01-15T10:30:45.123Z"
}
```

### Quick Checkout
⚡ **FASTEST** - Returns immediately, stock reduction is async (fire-and-forget).

**Endpoint:** `POST /api/checkout`

**Body:**
```json
{
  "userId": "user-123",
  "cartData": {
    "items": [
      {
        "cartItemId": "uuid",
        "productId": "P001",
        "productName": "Laptop Dell XPS 13",
        "price": 1299.99,
        "quantity": 1
      }
    ],
    "total": 1299.99
  }
}
```

**Response:** (Immediate - ~30ms, stock reduction happens async in background)
```json
{
  "success": true,
  "message": "Order created successfully - Stock reduction in progress",
  "data": {
    "orderId": "uuid",
    "userId": "user-123",
    "itemCount": 1,
    "total": 1299.99,
    "status": "PROCESSING",
    "createdAt": "2024-01-15T10:30:45.123Z"
  },
  "timestamp": "2024-01-15T10:30:45.123Z"
}
```

### Validated Checkout
✅ **RECOMMENDED** - Checks stock availability first, then processes order.

**Endpoint:** `POST /api/checkout/validated`

**Body:** (Same as Quick Checkout)

**Response:** (Immediate if stock available - ~50ms)
```json
{
  "success": true,
  "message": "Order confirmed successfully",
  "data": {
    "orderId": "uuid",
    "userId": "user-123",
    "itemCount": 1,
    "total": 1299.99,
    "status": "CONFIRMED",
    "createdAt": "2024-01-15T10:30:45.123Z"
  },
  "timestamp": "2024-01-15T10:30:45.123Z"
}
```

**Error Response (Insufficient Stock):**
```json
{
  "success": false,
  "message": "Insufficient stock for some items",
  "data": [
    {
      "productId": "P001",
      "available": false,
      "currentStock": 1,
      "requestedQuantity": 5
    }
  ]
}
```

### Health Check
**Endpoint:** `GET /health`

---

## Error Handling

### Common Error Responses

**400 Bad Request:**
```json
{
  "success": false,
  "message": "Invalid checkout data"
}
```

**404 Not Found:**
```json
{
  "success": false,
  "message": "Product not found"
}
```

**500 Server Error:**
```json
{
  "success": false,
  "message": "Error processing request",
  "error": "Detailed error message"
}
```

---

## Performance Notes

⚡ **Fast Operations:**
- Add to Cart: ~10ms
- Quick Checkout: ~30ms
- Reduce Stock: Async (no wait)

📊 **Scalability:**
- Handles hundreds of concurrent requests
- No slowdown with load
- Background processing doesn't block responses

---

## Status Codes

| Code | Meaning |
|------|---------|
| 200 | Success (GET) |
| 201 | Created (POST success) |
| 400 | Bad Request |
| 404 | Not Found |
| 500 | Server Error |
| 503 | Service Unavailable |

---

## Implementation Tips

1. **For Real-time Feedback:**
   - Use Quick Checkout (`/checkout`)
   - Don't wait for stock reduction

2. **For Data Consistency:**
   - Use Validated Checkout (`/checkout/validated`)
   - Wait for response before clearing cart

3. **For Monitoring:**
   - Check health endpoints regularly
   - Monitor Redis memory usage
   - Track order statuses

4. **For Debugging:**
   - Use browser dev tools (Network tab)
   - Check service logs with `docker-compose logs`
   - Monitor Redis keys with `redis-cli`
