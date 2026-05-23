# BOOKING + PAYMENT SERVICE - Người 5

**Quản lý booking và thanh toán**

## Chức năng

### Booking Service

- ✅ Tạo booking (`POST /bookings`)
- ✅ Lấy chi tiết booking (`GET /bookings/:id`)

Port: **8083**

### Payment Service

- ✅ Xử lý thanh toán (`POST /payments`)
- ✅ Lấy chi tiết payment (`GET /payments/:id`)
- ✅ Random success/fail (80% thành công, 20% thất bại)

Port: **8084**

## Chạy riêng lẻ

### Booking Service

```bash
npm install
npm run booking
```

### Payment Service

```bash
npm install
npm run payment
```

## Test API

### Tạo booking

```bash
curl -X POST http://localhost:8083/bookings \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "tourId": 1,
    "quantity": 2,
    "email": "user1@example.com",
    "tourPrice": 2500000
  }'
```

### Thanh toán

```bash
curl -X POST http://localhost:8084/payments \
  -H "Content-Type: application/json" \
  -d '{
    "bookingId": "BK-XXXXXX",
    "amount": 5000000,
    "email": "user1@example.com"
  }'
```

## Note

- Chỉ có Orchestrator gọi service này
- Không gọi service khác
- Payment có 80% thành công, 20% thất bại (random)
