# 💻 POSTMAN COLLECTION - Test API

[Click để import Postman Collection]

## Base URL

```
http://localhost:8080
```

---

## 1. User Service

### 1.1 Login

```
POST http://localhost:8081/login
Content-Type: application/json

{
  "username": "user1",
  "password": "123456"
}
```

### 1.2 Get User

```
GET http://localhost:8081/users/1
```

### 1.3 Register User

```
POST http://localhost:8081/users
Content-Type: application/json

{
  "username": "user4",
  "password": "newpass",
  "email": "user4@example.com",
  "fullName": "New User"
}
```

---

## 2. Tour Service

### 2.1 Get All Tours

```
GET http://localhost:8082/tours
```

### 2.2 Get Tour Detail

```
GET http://localhost:8082/tours/1
```

---

## 3. Booking Service

### 3.1 Create Booking

```
POST http://localhost:8083/bookings
Content-Type: application/json

{
  "userId": 1,
  "tourId": 1,
  "quantity": 2,
  "email": "user1@example.com",
  "tourPrice": 2500000
}
```

### 3.2 Get Booking

```
GET http://localhost:8083/bookings/BK-XXXXXX
```

---

## 4. Payment Service

### 4.1 Create Payment

```
POST http://localhost:8084/payments
Content-Type: application/json

{
  "bookingId": "BK-XXXXXX",
  "amount": 5000000,
  "email": "user1@example.com"
}
```

### 4.2 Get Payment

```
GET http://localhost:8084/payments/PAY-XXXXXX
```

---

## 5. Orchestrator Service

### 5.1 Login (via Orchestrator)

```
POST http://localhost:8080/login
Content-Type: application/json

{
  "username": "user1",
  "password": "123456"
}
```

### 5.2 Get Tours (via Orchestrator)

```
GET http://localhost:8080/tours
```

### 5.3 Get Tour Detail (via Orchestrator)

```
GET http://localhost:8080/tours/1
```

### 5.4 ⭐ MAIN FLOW - Book Tour (via Orchestrator)

```
POST http://localhost:8080/book-tour
Content-Type: application/json

{
  "userId": 1,
  "tourId": 1,
  "quantity": 2,
  "email": "user1@example.com"
}
```

**Response Success:**

```json
{
  "success": true,
  "bookingId": "BK-ABC123",
  "userId": 1,
  "tourName": "Tour Hà Nội - Hạ Long",
  "quantity": 2,
  "totalPrice": 5000000,
  "email": "user1@example.com",
  "paymentStatus": "success",
  "message": "✅ Đặt tour thành công!"
}
```

**Response Failed:**

```json
{
  "success": false,
  "bookingId": "BK-ABC123",
  "tourName": "Tour Hà Nội - Hạ Long",
  "quantity": 2,
  "totalPrice": 5000000,
  "paymentStatus": "failed",
  "message": "⚠️ Booking được tạo nhưng thanh toán thất bại"
}
```

### 5.5 Health Check

```
GET http://localhost:8080/health
```

---

## Test Data

### Users

- user1 / 123456
- user2 / password123
- user3 / pass456

### Tours

- Tour 1: Hà Nội - Hạ Long (2,500,000 VND)
- Tour 2: Sài Gòn - Cần Thơ (1,500,000 VND)
- Tour 3: Đà Nẵng - Hội An (3,000,000 VND)
- Tour 4: Phú Quốc (2,000,000 VND)
- Tour 5: Sapa - Fansipan (1,800,000 VND)

---

## cURL Examples

### Full Flow in cURL

```bash
#!/bin/bash

# 1. Login
echo "1. Login..."
LOGIN=$(curl -s -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user1","password":"123456"}')

echo $LOGIN

# 2. Get Tours
echo -e "\n2. Get Tours..."
TOURS=$(curl -s http://localhost:8080/tours)
echo $TOURS

# 3. Book Tour
echo -e "\n3. Book Tour..."
BOOKING=$(curl -s -X POST http://localhost:8080/book-tour \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "tourId": 1,
    "quantity": 2,
    "email": "user1@example.com"
  }')

echo $BOOKING
```

Save as `test-flow.sh` và run:

```bash
chmod +x test-flow.sh
./test-flow.sh
```

---

## PowerShell Examples (Windows)

```powershell
# 1. Login
$loginResponse = Invoke-WebRequest -Uri "http://localhost:8080/login" `
  -Method POST `
  -Headers @{"Content-Type"="application/json"} `
  -Body '{"username":"user1","password":"123456"}' | ConvertTo-Json

Write-Host $loginResponse

# 2. Book Tour
$bookingResponse = Invoke-WebRequest -Uri "http://localhost:8080/book-tour" `
  -Method POST `
  -Headers @{"Content-Type"="application/json"} `
  -Body '{
    "userId": 1,
    "tourId": 1,
    "quantity": 2,
    "email": "user1@example.com"
  }' | ConvertTo-Json

Write-Host $bookingResponse
```

---

## Postman Collection JSON

```json
{
  "info": {
    "name": "Travel Booking System",
    "description": "Orchestration-Driven SOA",
    "version": "1.0"
  },
  "item": [
    {
      "name": "Book Tour (Main Flow)",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "url": {
          "raw": "{{baseUrl}}/book-tour",
          "host": ["{{baseUrl}}"],
          "path": ["book-tour"]
        },
        "body": {
          "mode": "raw",
          "raw": "{\n  \"userId\": 1,\n  \"tourId\": 1,\n  \"quantity\": 2,\n  \"email\": \"user1@example.com\"\n}"
        }
      }
    }
  ],
  "variable": [
    {
      "key": "baseUrl",
      "value": "http://localhost:8080"
    }
  ]
}
```

---

**Tip:** Dùng environment variables trong Postman để dễ chuyển giữa environments (dev/test/prod).
