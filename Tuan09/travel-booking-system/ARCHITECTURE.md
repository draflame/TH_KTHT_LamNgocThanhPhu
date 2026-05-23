# 🏗️ KIẾN TRÚC HỆ THỐNG - ARCHITECTURE GUIDE

## Tổng Quan Kiến Trúc

```
┌─────────────────────────────────────────────────────────────┐
│                    CLIENT LAYER                             │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Frontend - React (3000)                             │  │
│  │  - Login UI                                          │  │
│  │  - Tour List                                         │  │
│  │  - Booking Form                                      │  │
│  └────────────────┬─────────────────────────────────────┘  │
└─────────────────┼──────────────────────────────────────────┘
                  │
                  │ HTTP REST API
                  ▼
┌─────────────────────────────────────────────────────────────┐
│             ORCHESTRATION LAYER                             │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Orchestrator Service (8080) - Express              │  │
│  │  ✓ Điều phối toàn bộ flow                           │  │
│  │  ✓ Gọi các services khác                            │  │
│  │  ✓ Implement business logic                         │  │
│  │  ✓ Transaction management                           │  │
│  └──┬──────────┬──────────┬──────────────┬─────────────┘  │
└─────┼──────────┼──────────┼──────────────┼─────────────────┘
      │          │          │              │
      ▼          ▼          ▼              ▼
    ┌────┐    ┌────┐    ┌────┐        ┌────────┐
    │    │    │    │    │    │        │        │
┌───┴────▼───┴────▼───┴────▼────────┴───────┐
│          SERVICE LAYER                     │
├──────────────────────────────────────────┤
│                                          │
│ ┌─────────────────────────────────────┐ │
│ │ User Service (8081) - Express       │ │
│ │ ✓ POST /login                       │ │
│ │ ✓ GET /users/:id                    │ │
│ │ ✓ POST /users (register)            │ │
│ └─────────────────────────────────────┘ │
│                                          │
│ ┌─────────────────────────────────────┐ │
│ │ Tour Service (8082) - Express       │ │
│ │ ✓ GET /tours                        │ │
│ │ ✓ GET /tours/:id                    │ │
│ └─────────────────────────────────────┘ │
│                                          │
│ ┌─────────────────────────────────────┐ │
│ │ Booking Service (8083) - Express    │ │
│ │ ✓ POST /bookings                    │ │
│ │ ✓ GET /bookings/:id                 │ │
│ └─────────────────────────────────────┘ │
│                                          │
│ ┌─────────────────────────────────────┐ │
│ │ Payment Service (8084) - Express    │ │
│ │ ✓ POST /payments                    │ │
│ │ ✓ GET /payments/:id                 │ │
│ └─────────────────────────────────────┘ │
│                                          │
└──────────────────────────────────────────┘
```

---

## Orchestration-Driven SOA

### Nguyên Tắc Kiến Trúc

1. **Một Orchestrator Trung Tâm**
   - Tất cả flow đều qua Orchestrator
   - Orchestrator kiểm soát toàn bộ logic

2. **Services Không Gọi Nhau**
   - Services chỉ được gọi từ Orchestrator
   - Services không gọi services khác trực tiếp
   - Tránh circular dependencies

3. **Frontend Chỉ Gọi Orchestrator**
   - Frontend không biết về các services khác
   - Toàn bộ logic backend trong Orchestrator
   - Dễ thay đổi, mở rộng

4. **REST API Communication**
   - Tất cả là HTTP REST API
   - JSON format
   - Dễ debug, test

---

## Flow Chi Tiết: Book Tour

### Request → Response Path

```
[Frontend]
   │
   ├─ POST /book-tour
   │  {
   │    "userId": 1,
   │    "tourId": 1,
   │    "quantity": 2,
   │    "email": "user1@example.com"
   │  }
   │
   ▼
[Orchestrator - 8080]
   │
   ├─ STEP 1: Validate User
   │  │
   │  └─ GET http://localhost:8081/users/1
   │     ↓
   │     [User Service]
   │     ← Response: { user object }
   │
   ├─ STEP 2: Get Tour Info
   │  │
   │  └─ GET http://localhost:8082/tours/1
   │     ↓
   │     [Tour Service]
   │     ← Response: { tour object with price }
   │
   ├─ STEP 3: Create Booking
   │  │
   │  └─ POST http://localhost:8083/bookings
   │     {
   │       "userId": 1,
   │       "tourId": 1,
   │       "quantity": 2,
   │       "email": "user1@example.com",
   │       "tourPrice": 2500000
   │     }
   │     ↓
   │     [Booking Service]
   │     ← Response: { bookingId, booking object }
   │
   ├─ STEP 4: Process Payment
   │  │
   │  └─ POST http://localhost:8084/payments
   │     {
   │       "bookingId": "BK-ABC123",
   │       "amount": 5000000,
   │       "email": "user1@example.com"
   │     }
   │     ↓
   │     [Payment Service]
   │     ← Response: { success: true/false, paymentStatus }
   │
   └─ STEP 5: Aggregate Results
      {
        "success": true,
        "bookingId": "BK-ABC123",
        "tourName": "Tour Hà Nội - Hạ Long",
        "quantity": 2,
        "totalPrice": 5000000,
        "paymentStatus": "success"
      }
      │
      ▼
[Frontend]
   │
   └─ Show Success/Error Message
```

---

## Ưu Điểm của Orchestration Pattern

| Ưu Điểm                       | Mô Tả                                       |
| ----------------------------- | ------------------------------------------- |
| **Tập Trung Kiểm Soát**       | Toàn bộ flow logic ở một chỗ (Orchestrator) |
| **Dễ Theo Dõi**               | Dễ debug, dễ xem flow thực thi              |
| **Tránh Circular Dependency** | Services không gọi nhau                     |
| **Dễ Mở Rộng**                | Thêm step mới chỉ cần update Orchestrator   |
| **Độc Lập Services**          | Mỗi service làm 1 việc duy nhất             |
| **Dễ Test**                   | Có thể test Orchestrator flow riêng         |

---

## Nhược Điểm & Giải Pháp

| Nhược Điểm                        | Giải Pháp                               |
| --------------------------------- | --------------------------------------- |
| **Orchestrator thành bottleneck** | Tối ưu code, caching, async processing  |
| **Single point of failure**       | Implement health check, circuit breaker |
| **Request quá nhiều**             | Batch operations, composite patterns    |
| **Timeout khi services chậm**     | Set timeout, retry logic                |

---

## Error Handling & Resilience

### Orchestrator Error Handling

```javascript
// Khi 1 trong 5 services fail

STEP 1: ✅ Validate user
STEP 2: ✅ Get tour info
STEP 3: ✅ Create booking
STEP 4: ❌ Payment failed
        → Booking vẫn tạo được
        → Payment thất bại
        → Return: { success: false, paymentStatus: 'failed' }
STEP 5: ❌ Không gửi notification

// Có thể implement rollback:
- Nếu payment fail → delete booking (saga pattern)
```

### Retry Logic (có thể implement)

```javascript
async function callServiceWithRetry(url, data, retries = 3) {
  for (let i = 0; i < retries; i++) {
    try {
      return await axios.post(url, data);
    } catch (error) {
      if (i === retries - 1) throw error;
      await sleep(1000 * (i + 1)); // Exponential backoff
    }
  }
}
```

---

## Database Design (Nếu có)

### Users Table

```sql
CREATE TABLE users (
  id INT PRIMARY KEY,
  username VARCHAR(50) UNIQUE,
  password VARCHAR(100),
  email VARCHAR(100),
  fullName VARCHAR(100)
);
```

### Tours Table

```sql
CREATE TABLE tours (
  id INT PRIMARY KEY,
  name VARCHAR(100),
  price INT,
  duration INT,
  description TEXT
);
```

### Bookings Table

```sql
CREATE TABLE bookings (
  id INT PRIMARY KEY,
  bookingId VARCHAR(50) UNIQUE,
  userId INT FOREIGN KEY,
  tourId INT FOREIGN KEY,
  quantity INT,
  totalPrice INT,
  status VARCHAR(20),
  createdAt DATETIME
);
```

### Payments Table

```sql
CREATE TABLE payments (
  id INT PRIMARY KEY,
  paymentId VARCHAR(50) UNIQUE,
  bookingId VARCHAR(50),
  amount INT,
  status VARCHAR(20),
  createdAt DATETIME
);
```

---

## Mở Rộng trong Tương Lai

### 1. Thêm Notification Service

```
Orchestrator
    ├─ [Existing services]
    └─ ✨ POST /send-notification (Email/SMS)
```

### 2. Thêm Review Service

```
Tour Service
    ├─ GET /tours/:id
    ├─ GET /tours/:id/reviews
    └─ ✨ POST /reviews
```

### 3. Thêm Analytics Service

```
Orchestrator
    ├─ POST /book-tour
    ├─ [Existing steps]
    └─ ✨ POST /analytics/booking-created
```

### 4. Thêm Payment Gateway

```
Payment Service
    ├─ POST /payments
    ├─ ✨ Integration với Stripe/PayPal
    └─ ✨ Webhook handling
```

---

## Best Practices

1. ✅ **Logging & Monitoring**
   - Log mỗi step trong Orchestrator
   - Track execution time
   - Alert khi có error

2. ✅ **Health Check**
   - Implement `/health` endpoint trên mỗi service
   - Orchestrator kiểm tra services khác

3. ✅ **Caching**
   - Cache danh sách tour (không thay đổi thường)
   - Cache user info sau khi validate

4. ✅ **Rate Limiting**
   - Protect services khỏi brute force
   - Limit requests per user

5. ✅ **Security**
   - Validate input trên Orchestrator & Services
   - Use HTTPS in production
   - Implement authentication tokens

---

## Deployment (Production)

### Docker Compose

```yaml
version: "3.8"
services:
  user-service:
    image: travel-booking-user:latest
    ports: ["8081:8081"]
  tour-service:
    image: travel-booking-tour:latest
    ports: ["8082:8082"]
  # ... other services
```

### Kubernetes

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: orchestrator-service
spec:
  replicas: 3
  template:
    spec:
      containers:
        - name: orchestrator
          image: travel-booking-orchestrator:latest
          ports:
            - containerPort: 8080
```

---

## Tài Liệu Tham Khảo

- [Martin Fowler - SOA Pattern](https://martinfowler.com/articles/patterns-of-distributed-systems/)
- [Microservices Patterns - Orchestration vs Choreography](https://microservices.io/patterns/data/saga.html)
- [REST API Best Practices](https://restfulapi.net/)

---

**Kiến trúc này được thiết kế cho học tập. Trong production, cân nhắc:**

- Thêm database
- Thêm message queue (RabbitMQ, Kafka)
- Thêm caching layer (Redis)
- Implement circuit breaker pattern
- Thêm API Gateway
