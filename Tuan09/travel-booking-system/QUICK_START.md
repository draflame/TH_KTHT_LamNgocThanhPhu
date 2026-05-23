# 🚀 QUICK START (5 phút để bắt đầu)

## 📦 Prerequisites

- Node.js v16+
- npm hoặc yarn
- Git

## ⚡ Cách 1: Docker Compose (Dễ nhất - 2 phút)

```bash
# Vào thư mục project
cd travel-booking-system

# Chạy tất cả services
docker-compose up -d

# Xem logs (optional)
docker-compose logs -f

# Mở browser
http://localhost:3000

# Login: user1 / 123456
```

Done! 🎉

---

## ⚡ Cách 2: Chạy Manual (3 phút)

### Terminal 1: User Service

```bash
cd user-service
npm install
npm start
# ✅ http://localhost:8081
```

### Terminal 2: Tour Service

```bash
cd tour-service
npm install
npm start
# ✅ http://localhost:8082
```

### Terminal 3: Booking Service

```bash
cd booking-payment-service
npm install
npm run booking
# ✅ http://localhost:8083
```

### Terminal 4: Payment Service

```bash
cd booking-payment-service
npm install
npm run payment
# ✅ http://localhost:8084
```

### Terminal 5: Orchestrator Service

```bash
cd orchestrator-service
npm install
npm start
# ✅ http://localhost:8080
```

### Terminal 6: Frontend

```bash
cd frontend
npm install
npm start
# ✅ http://localhost:3000
```

---

## 🌐 Access

- **Frontend:** http://localhost:3000
- **Orchestrator:** http://localhost:8080
- **User Service:** http://localhost:8081
- **Tour Service:** http://localhost:8082
- **Booking Service:** http://localhost:8083
- **Payment Service:** http://localhost:8084

---

## 🧪 Test Flow

### 1. Đăng nhập

```bash
user1 / 123456
```

### 2. Xem tour

```
Click "Xem tour" hoặc navigate tới list
```

### 3. Đặt tour

```
Chọn tour → Nhập số lượng → Click "Xác nhận"
```

### 4. Thành công!

```
Xem booking ID và payment status
```

---

## 🧪 Test bằng cURL

```bash
# Test main flow
curl -X POST http://localhost:8080/book-tour \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "tourId": 1,
    "quantity": 2,
    "email": "user1@example.com"
  }'
```

---

## 📚 Tài Liệu Chi Tiết

- [GETTING_STARTED.md](GETTING_STARTED.md) - Hướng dẫn chi tiết
- [README.md](README.md) - Overview
- [ARCHITECTURE.md](ARCHITECTURE.md) - Kiến trúc hệ thống
- [TEAM_ASSIGNMENT.md](TEAM_ASSIGNMENT.md) - Phân công 5 người
- [API_TESTING.md](API_TESTING.md) - Test API
- [TESTING.md](TESTING.md) - Testing guide

---

## 🚨 Troubleshooting

### Port already in use

```bash
# Kill process (Windows)
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Kill process (Mac/Linux)
lsof -i :8080
kill -9 <PID>
```

### Dependencies error

```bash
rm -rf node_modules
npm install
```

### CORS error

Chắc chắn CORS đã enable trong Express

### Can't connect to services

```bash
# Check all services
curl http://localhost:8080/health
curl http://localhost:8081/health
curl http://localhost:8082/health
curl http://localhost:8083/health
curl http://localhost:8084/health
```

---

## 📋 Demo Data

### Users

- user1 / 123456
- user2 / password123

### Tours

- Tour 1: Hà Nội - Hạ Long (2,500,000 VND)
- Tour 2: Sài Gòn - Cần Thơ (1,500,000 VND)
- Tour 3: Đà Nẵng - Hội An (3,000,000 VND)
- Tour 4: Phú Quốc (2,000,000 VND)
- Tour 5: Sapa - Fansipan (1,800,000 VND)

---

## 🔄 Flow Overview

```
Frontend (React 3000)
    ↓
Orchestrator (8080)
    ├→ User Service (8081)
    ├→ Tour Service (8082)
    ├→ Booking Service (8083)
    └→ Payment Service (8084)
    ↓
Response to Frontend
```

---

## ✅ Checklist

- [ ] Docker installed (nếu dùng docker-compose)
- [ ] Node.js installed
- [ ] Tất cả services chạy
- [ ] Ports không bị occupied
- [ ] Có thể access localhost:3000
- [ ] Có thể login
- [ ] Có thể xem tour
- [ ] Có thể đặt tour
- [ ] Payment success hoặc fail

---

## 🎉 Success!

Nếu thấy cảnh đặt tour thành công với booking ID, tức là hệ thống đang hoạt động! 🚀

---

## 📞 Support

- Check logs trong mỗi terminal
- Read docs: GETTING_STARTED.md, ARCHITECTURE.md
- Test API bằng Postman: API_TESTING.md

Happy coding! 💻
