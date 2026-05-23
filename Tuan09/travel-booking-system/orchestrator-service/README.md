# ORCHESTRATOR SERVICE - Người 2

**⭐ Điều phối toàn bộ flow**

## Chức năng

- ✅ Điều phối tất cả các services
- ✅ Nhận request từ Frontend
- ✅ Gọi các services khác
- ✅ Trả kết quả về Frontend

## Chạy

```bash
npm install
npm start
```

Port: **8080**

## API Endpoints

### Pass Through (Forward to Services)

- `POST /login` → User Service
- `GET /tours` → Tour Service
- `GET /tours/:id` → Tour Service

### ⭐ Main Flow (Orchestration)

- `POST /book-tour` → Orchestrate 5 steps

## ⭐ Flow Orchestration Chi Tiết

### POST /book-tour

Flow đặt tour:

```
Frontend
   ↓
Orchestrator (8080)
   ├─→ 1. GET /users/{userId} (User Service:8081)
   ├─→ 2. GET /tours/{tourId} (Tour Service:8082)
   ├─→ 3. POST /bookings (Booking Service:8083)
   ├─→ 4. POST /payments (Payment Service:8084)
   ↓
Frontend (Response)
```

Request:

```json
{
  "userId": 1,
  "tourId": 1,
  "quantity": 2,
  "email": "user@example.com"
}
```

Response:

```json
{
  "success": true,
  "bookingId": "BK-ABC123",
  "userId": 1,
  "tourName": "Tour Hà Nội - Hạ Long",
  "quantity": 2,
  "totalPrice": 5000000,
  "email": "user@example.com",
  "paymentStatus": "success",
  "message": "✅ Đặt tour thành công!"
}
```

## Test

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

## Note

- ✅ Orchestrator là trung tâm
- ✅ Tất cả services khác không gọi nhau
- ✅ Frontend chỉ gọi Orchestrator
- ✅ Sử dụng REST API
- ✅ Dễ debug với Postman/Insomnia
