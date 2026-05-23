# 📘 HƯỚNG DẪN CHẠY HỆ THỐNG - TRAVEL BOOKING SYSTEM

## 🎯 Mục đích

Hệ thống đặt tour với kiến trúc **Orchestration-Driven SOA** cho 5 người làm việc độc lập.

---

## 📋 Cấu Trúc Phân Công

```
Người 1: Frontend (ReactJS)                  Port 3000
    ↓
Người 2: Orchestrator Service (Express)      Port 8080
    ↓ (điều phối)
    ├→ Người 3: User Service (Express)       Port 8081
    ├→ Người 4: Tour Service (Express)       Port 8082
    └→ Người 5: Booking + Payment (Express)  Port 8083-8084
```

---

## ⚡ Cách 1: Chạy từng service riêng (Dễ nhất)

### Bước 1: Terminal 1 - User Service (Người 3)

```bash
cd user-service
npm install
npm start
```

Output:

```
👥 USER SERVICE - Người 3
✅ Server running on http://localhost:8081
```

### Bước 2: Terminal 2 - Tour Service (Người 4)

```bash
cd tour-service
npm install
npm start
```

Output:

```
🗺️  TOUR SERVICE - Người 4
✅ Server running on http://localhost:8082
```

### Bước 3: Terminal 3 - Booking Service (Người 5)

```bash
cd booking-payment-service
npm install
npm run booking
```

Output:

```
📅 BOOKING SERVICE - Người 5
✅ Server running on http://localhost:8083
```

### Bước 4: Terminal 4 - Payment Service (Người 5)

```bash
cd booking-payment-service
npm install
npm run payment
```

Output:

```
💳 PAYMENT SERVICE - Người 5
✅ Server running on http://localhost:8084
```

### Bước 5: Terminal 5 - Orchestrator Service (Người 2)

```bash
cd orchestrator-service
npm install
npm start
```

Output:

```
⭐ ORCHESTRATOR SERVICE - Người 2
✅ Server running on http://localhost:8080
```

### Bước 6: Terminal 6 - Frontend (Người 1)

```bash
cd frontend
npm install
npm start
```

Output:

```
Compiled successfully!
You can now view travel-booking-frontend in the browser.
http://localhost:3000
```

---

## 🐳 Cách 2: Chạy với Docker Compose

```bash
# Từ thư mục gốc
docker-compose up -d

# Xem logs
docker-compose logs -f

# Dừng
docker-compose down
```

Tất cả services sẽ chạy cùng lúc!

---

## 🧪 Test Flow Đặt Tour

### Step 1: Đăng nhập (thông qua Orchestrator)

```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user1",
    "password": "123456"
  }'
```

Response:

```json
{
  "success": true,
  "user": {
    "id": 1,
    "username": "user1",
    "email": "user1@example.com"
  },
  "message": "Đăng nhập thành công"
}
```

### Step 2: Lấy danh sách tour

```bash
curl http://localhost:8080/tours
```

### Step 3: ⭐ Đặt tour (MAIN FLOW)

```bash
curl -X POST http://localhost:8080/book-tour \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "tourId": 1,
    "quantity": 2,
    "email": "user1@example.com"
  }'
```

Response:

```json
{
  "success": true,
  "bookingId": "BK-ABC123",
  "tourName": "Tour Hà Nội - Hạ Long",
  "quantity": 2,
  "totalPrice": 5000000,
  "paymentStatus": "success",
  "message": "✅ Đặt tour thành công!"
}
```

---

## 🌐 Chạy trên UI (Dễ hơn)

### 1. Mở browser

```
http://localhost:3000
```

### 2. Đăng nhập

- Username: `user1`
- Password: `123456`

### 3. Chọn tour và đặt

- Xem danh sách tour
- Chọn tour
- Nhập số lượng
- Xác nhận đặt

---

## 📊 Flow Chi Tiết Khi Đặt Tour

```
Frontend (React)
   ↓ (POST /book-tour)
Orchestrator (8080)
   ├─→ Step 1: Validate user
   │      ↓
   │   User Service (8081)
   │   GET /users/1
   │
   ├─→ Step 2: Get tour info
   │      ↓
   │   Tour Service (8082)
   │   GET /tours/1
   │
   ├─→ Step 3: Create booking
   │      ↓
   │   Booking Service (8083)
   │   POST /bookings
   │
   ├─→ Step 4: Process payment
   │      ↓
   │   Payment Service (8084)
   │   POST /payments (80% success)
   │
   └─→ Return result
      ↓
   Frontend (success/failed)
```

---

## 📝 Demo Data

### Users

```
user1 / 123456 → user1@example.com
user2 / password123 → user2@example.com
user3 / pass456 → user3@example.com
```

### Tours

```
1. Tour Hà Nội - Hạ Long (2,500,000 VND)
2. Tour Sài Gòn - Cần Thơ (1,500,000 VND)
3. Tour Đà Nẵng - Hội An (3,000,000 VND)
4. Tour Phú Quốc (2,000,000 VND)
5. Tour Sapa - Fansipan (1,800,000 VND)
```

---

## 🔍 Debugging

### Xem logs của Orchestrator

```bash
# Khi đặt tour, Orchestrator sẽ print:
⭐ [ORCHESTRATOR] POST /book-tour - MAIN FLOW START
📝 STEP 1: Validate user từ User Service
✅ [STEP 1] User validation success
🗺️  STEP 2: Lấy thông tin tour từ Tour Service
...
```

### Test từng service riêng

```bash
# Test User Service
curl http://localhost:8081/users/1

# Test Tour Service
curl http://localhost:8082/tours

# Test Booking Service
curl -X POST http://localhost:8083/bookings \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"tourId":1,"quantity":2,"email":"user@example.com","tourPrice":2500000}'

# Test Payment Service (80% success)
curl -X POST http://localhost:8084/payments \
  -H "Content-Type: application/json" \
  -d '{"bookingId":"BK-123","amount":5000000,"email":"user@example.com"}'
```

---

## ⚠️ Lưu ý quan trọng

1. ✅ **Frontend chỉ gọi Orchestrator** - không gọi service khác
2. ✅ **Orchestrator gọi các services** - qua REST API
3. ❌ **Services KHÔNG gọi nhau** - chỉ được gọi từ Orchestrator
4. ✅ **Tất cả là REST** - không dùng gRPC hay message queue
5. ✅ **Payment random success/fail** - 80% thành công

---

## 🛠️ Troubleshooting

### Lỗi "Connection refused"

Đảm bảo tất cả services đang chạy:

```bash
# Kiểm tra từng port
curl http://localhost:8081/health
curl http://localhost:8082/health
curl http://localhost:8083/health
curl http://localhost:8084/health
curl http://localhost:8080/health
```

### Lỗi "Port already in use"

Kill process:

```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Mac/Linux
lsof -i :8080
kill -9 <PID>
```

### Frontend không connect Orchestrator

Kiểm tra `.env`:

```
REACT_APP_ORCHESTRATOR_URL=http://localhost:8080
```

---

## 📚 Tài liệu thêm

- [README.md](README.md) - Overview hệ thống
- [user-service/README.md](user-service/README.md) - Người 3
- [tour-service/README.md](tour-service/README.md) - Người 4
- [booking-payment-service/README.md](booking-payment-service/README.md) - Người 5
- [orchestrator-service/README.md](orchestrator-service/README.md) - Người 2
- [frontend/README.md](frontend/README.md) - Người 1

---

## 🎉 Success!

Khi thấy output này, hệ thống đã sẵn sàng:

```
⭐ ORCHESTRATOR SERVICE - Người 2
✅ Server running on http://localhost:8080
👥 USER SERVICE - Người 3
✅ Server running on http://localhost:8081
🗺️  TOUR SERVICE - Người 4
✅ Server running on http://localhost:8082
📅 BOOKING SERVICE - Người 5
✅ Server running on http://localhost:8083
💳 PAYMENT SERVICE - Người 5
✅ Server running on http://localhost:8084
```

Mở browser: `http://localhost:3000` 🎉
