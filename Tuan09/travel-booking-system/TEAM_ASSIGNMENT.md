# 📋 PHÂN CÔNG CHI TIẾT - 5 NGƯỜI

## 👤 Người 1: Frontend (ReactJS)

**Thư mục:** `frontend/`  
**Port:** 3000  
**Công nghệ:** React, Axios, CSS

### ✅ Nhiệm Vụ

- [x] Tạo UI Login
- [x] Tạo UI Tours List
- [x] Tạo UI Booking Form
- [x] Tạo UI Success Page
- [x] Implement routing giữa các page
- [x] Call Orchestrator API
- [x] Error handling
- [x] Responsive design

### 📝 API Gọi

**Chỉ gọi Orchestrator!**

```javascript
// src/services/api.js
POST   /login
GET    /tours
GET    /tours/:id
POST   /book-tour
```

### 🚀 Start

```bash
cd frontend
npm install
npm start
```

### 📦 Dependencies

- react, react-dom
- axios (HTTP client)
- react-router-dom (routing)

### 📋 Checklist

- [ ] Login form hoạt động
- [ ] Danh sách tour hiển thị
- [ ] Có thể chọn tour
- [ ] Booking form hoạt động
- [ ] Success page hiển thị
- [ ] Logout hoạt động
- [ ] Responsive trên mobile
- [ ] Không có console error

---

## 🎛️ Người 2: Orchestrator Service (Express.js)

**Thư mục:** `orchestrator-service/`  
**Port:** 8080  
**Công nghệ:** Node.js, Express, Axios

### ✅ Nhiệm Vụ

- [x] Nhận request từ Frontend
- [x] Forward login request → User Service
- [x] Forward tours request → Tour Service
- [x] Implement main booking flow:
  - [x] Validate user
  - [x] Get tour info
  - [x] Create booking
  - [x] Process payment
  - [x] Aggregate results
- [x] Error handling
- [x] Logging

### 📝 API Endpoints

```javascript
POST /login           // Forward to User Service
GET  /tours           // Forward to Tour Service
GET  /tours/:id       // Forward to Tour Service
POST /book-tour       // ⭐ MAIN ORCHESTRATION FLOW
GET  /health
```

### ⭐ Main Flow: /book-tour

```
Request
  ↓
Step 1: GET /users/{userId}
  ↓
Step 2: GET /tours/{tourId}
  ↓
Step 3: POST /bookings
  ↓
Step 4: POST /payments
  ↓
Aggregate & Return Response
```

### 🚀 Start

```bash
cd orchestrator-service
npm install
npm start
```

### 📋 Checklist

- [ ] Server chạy port 8080
- [ ] Call User Service đúng
- [ ] Call Tour Service đúng
- [ ] Call Booking Service đúng
- [ ] Call Payment Service đúng
- [ ] Aggregate results đúng
- [ ] Error handling
- [ ] Logging chi tiết

---

## 👥 Người 3: User Service (Express.js)

**Thư mục:** `user-service/`  
**Port:** 8081  
**Công nghệ:** Node.js, Express

### ✅ Nhiệm Vụ

- [x] Quản lý user
- [x] Implement login API
- [x] Implement get user API
- [x] Implement register API (tùy chọn)
- [x] Mock data users

### 📝 API Endpoints

```javascript
POST /login        // Đăng nhập
GET  /users/:id    // Lấy user info
POST /users        // Tạo user mới (tùy chọn)
GET  /health       // Health check
```

### 📋 Request/Response

**POST /login**

```json
Request:
{
  "username": "user1",
  "password": "123456"
}

Response:
{
  "success": true,
  "user": {
    "id": 1,
    "username": "user1",
    "email": "user1@example.com"
  }
}
```

### 🚀 Start

```bash
cd user-service
npm install
npm start
```

### 📋 Checklist

- [ ] Server chạy port 8081
- [ ] Login hoạt động
- [ ] Get user hoạt động
- [ ] Validate input
- [ ] Mock data có sẵn
- [ ] Error handling
- [ ] CORS enabled

---

## 🗺️ Người 4: Tour Service (Express.js)

**Thư mục:** `tour-service/`  
**Port:** 8082  
**Công nghệ:** Node.js, Express

### ✅ Nhiệm Vụ

- [x] Quản lý tour
- [x] Implement get tours API
- [x] Implement get tour detail API
- [x] Mock data tours

### 📝 API Endpoints

```javascript
GET /tours         // Danh sách tour
GET /tours/:id     // Chi tiết tour
GET /health        // Health check
```

### 📋 Response Format

**GET /tours**

```json
{
  "success": true,
  "tours": [
    {
      "id": 1,
      "name": "Tour Hà Nội - Hạ Long",
      "price": 2500000,
      "duration": 3,
      "description": "..."
    }
  ]
}
```

### 📝 Mock Tours

```
1. Tour Hà Nội - Hạ Long (2,500,000 VND)
2. Tour Sài Gòn - Cần Thơ (1,500,000 VND)
3. Tour Đà Nẵng - Hội An (3,000,000 VND)
4. Tour Phú Quốc (2,000,000 VND)
5. Tour Sapa - Fansipan (1,800,000 VND)
```

### 🚀 Start

```bash
cd tour-service
npm install
npm start
```

### 📋 Checklist

- [ ] Server chạy port 8082
- [ ] Get tours hoạt động
- [ ] Get tour detail hoạt động
- [ ] Mock data hoàn chỉnh
- [ ] Error handling
- [ ] CORS enabled

---

## 💰 Người 5: Booking + Payment Service (Express.js)

**Thư mục:** `booking-payment-service/`  
**Port:** 8083 (Booking), 8084 (Payment)  
**Công nghệ:** Node.js, Express, UUID

### ✅ Nhiệm Vụ

**Booking Service:**

- [x] Implement create booking API
- [x] Implement get booking API
- [x] Generate booking ID

**Payment Service:**

- [x] Implement payment processing API
- [x] Implement random success/fail (80% success)
- [x] Implement get payment API

### 📝 API Endpoints

**Booking Service (8083)**

```javascript
POST /bookings      // Tạo booking
GET  /bookings/:id  // Lấy booking info
GET  /health
```

**Payment Service (8084)**

```javascript
POST /payments      // Xử lý thanh toán (random success/fail)
GET  /payments/:id  // Lấy payment info
GET  /health
```

### ⚙️ Random Payment Success/Fail

```javascript
// 80% success, 20% fail
const isSuccess = Math.random() > 0.2;

if (isSuccess) {
  // Return success response
} else {
  // Return failed response
}
```

### 📋 Request/Response

**POST /bookings**

```json
Request:
{
  "userId": 1,
  "tourId": 1,
  "quantity": 2,
  "email": "user@example.com",
  "tourPrice": 2500000
}

Response:
{
  "success": true,
  "bookingId": "BK-ABC123",
  "booking": { ... }
}
```

**POST /payments**

```json
Request:
{
  "bookingId": "BK-ABC123",
  "amount": 5000000,
  "email": "user@example.com"
}

Response (Success):
{
  "success": true,
  "paymentId": "PAY-XYZ789",
  "status": "success"
}

Response (Failed):
{
  "success": false,
  "paymentId": "PAY-XYZ789",
  "status": "failed"
}
```

### 🚀 Start

```bash
cd booking-payment-service
npm install

# Terminal 1: Booking Service
npm run booking

# Terminal 2: Payment Service (separate terminal)
npm run payment
```

### 📋 Checklist

- [ ] Booking Service chạy port 8083
- [ ] Payment Service chạy port 8084
- [ ] Create booking hoạt động
- [ ] Booking ID được sinh đúng
- [ ] Payment random success/fail
- [ ] Error handling
- [ ] CORS enabled

---

## 🔄 Quy Tắc Kiến Trúc

### ✅ Được phép

- ✅ Frontend gọi Orchestrator
- ✅ Orchestrator gọi các services khác
- ✅ Mỗi service gọi database riêng (nếu có)
- ✅ Services trả về kết quả dạng JSON

### ❌ Không được

- ❌ Frontend gọi services trực tiếp
- ❌ Services gọi nhau trực tiếp
- ❌ Services gọi database nhau
- ❌ Gửi dữ liệu không phải JSON

---

## 📞 Giao Tiếp Giữa 5 Người

| Từ           | Đến             | Loại     |
| ------------ | --------------- | -------- |
| Frontend     | Orchestrator    | REST API |
| Orchestrator | User Service    | REST API |
| Orchestrator | Tour Service    | REST API |
| Orchestrator | Booking Service | REST API |
| Orchestrator | Payment Service | REST API |

**Không có giao tiếp khác!**

---

## 🎯 Timeline Khuyến Nghị

### Phase 1: Setup (Day 1)

- [ ] Mỗi người clone project
- [ ] Mỗi người chạy service của mình
- [ ] Test service hoạt động

### Phase 2: Integration (Day 2)

- [ ] Orchestrator gọi từng service
- [ ] Test flow đặt tour
- [ ] Fix errors

### Phase 3: Frontend (Day 3)

- [ ] Frontend gọi Orchestrator
- [ ] Test UI
- [ ] Polish & debugging

---

## 🚨 Support & Troubleshooting

### Issue: Connection refused

**Fix:** Kiểm tra tất cả services đang chạy

```bash
curl http://localhost:8080/health
curl http://localhost:8081/health
curl http://localhost:8082/health
curl http://localhost:8083/health
curl http://localhost:8084/health
```

### Issue: CORS error

**Fix:** Thêm CORS vào Express

```javascript
app.use(cors());
```

### Issue: JSON parse error

**Fix:** Kiểm tra Content-Type header

```javascript
app.use(bodyParser.json());
```

---

**Good luck! 🚀**
