# 🧪 TESTING GUIDE - Test Hệ Thống

## Công Cụ Test

### 1. cURL (Command Line)

```bash
# Thử trực tiếp từ Terminal
curl -X POST http://localhost:8080/book-tour \
  -H "Content-Type: application/json" \
  -d '{...}'
```

### 2. Postman

- Download từ: https://www.postman.com/downloads/
- Import collection từ project
- Có thể share team

### 3. Insomnia

- Download từ: https://insomnia.rest/
- Lightweight, dễ dùng

### 4. Browser DevTools

- F12 → Network tab
- Xem các requests từ Frontend

---

## Test Scenarios

### Test 1: User Service

#### 1.1 Đăng nhập thành công

```bash
curl -X POST http://localhost:8081/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user1",
    "password": "123456"
  }'
```

**Expected Response:**

```json
{
  "success": true,
  "user": {
    "id": 1,
    "username": "user1",
    "email": "user1@example.com"
  }
}
```

#### 1.2 Đăng nhập thất bại

```bash
curl -X POST http://localhost:8081/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user1",
    "password": "wrongpassword"
  }'
```

**Expected Response:**

```json
{
  "success": false,
  "message": "Username hoặc password không chính xác"
}
```

#### 1.3 Lấy user info

```bash
curl http://localhost:8081/users/1
```

---

### Test 2: Tour Service

#### 2.1 Danh sách tour

```bash
curl http://localhost:8082/tours
```

**Expected Response:**

```json
{
  "success": true,
  "tours": [
    {
      "id": 1,
      "name": "Tour Hà Nội - Hạ Long",
      "price": 2500000,
      "duration": 3
    },
    ...
  ],
  "total": 5
}
```

#### 2.2 Chi tiết tour

```bash
curl http://localhost:8082/tours/1
```

---

### Test 3: Booking Service

#### 3.1 Tạo booking

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

**Expected Response:**

```json
{
  "success": true,
  "bookingId": "BK-ABC12345",
  "booking": {
    "bookingId": "BK-ABC12345",
    "userId": 1,
    "tourId": 1,
    "quantity": 2,
    "totalPrice": 5000000
  }
}
```

---

### Test 4: Payment Service

#### 4.1 Thanh toán (80% success)

```bash
curl -X POST http://localhost:8084/payments \
  -H "Content-Type: application/json" \
  -d '{
    "bookingId": "BK-ABC12345",
    "amount": 5000000,
    "email": "user1@example.com"
  }'
```

**Expected Response (Success):**

```json
{
  "success": true,
  "paymentId": "PAY-XYZ789",
  "status": "success",
  "message": "Thanh toán thành công"
}
```

**Expected Response (Failed - 20% chance):**

```json
{
  "success": false,
  "paymentId": "PAY-XYZ789",
  "status": "failed",
  "message": "Thanh toán thất bại, vui lòng thử lại"
}
```

---

### Test 5: ⭐ Orchestrator - Main Flow

#### 5.1 Complete Book Tour Flow (Thành công)

**Request:**

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

**Expected Response (Payment Success):**

```json
{
  "success": true,
  "bookingId": "BK-ABC12345",
  "userId": 1,
  "tourName": "Tour Hà Nội - Hạ Long",
  "quantity": 2,
  "totalPrice": 5000000,
  "email": "user1@example.com",
  "paymentStatus": "success",
  "message": "✅ Đặt tour thành công!"
}
```

#### 5.2 Check Orchestrator Logs

Khi chạy test 5.1, Orchestrator sẽ print:

```
============================================================
⭐ [ORCHESTRATOR] POST /book-tour - MAIN FLOW START
============================================================
Request: { userId: 1, tourId: 1, quantity: 2, email: '...' }

📝 STEP 1: Validate user từ User Service
   [CALL] GET http://localhost:8081/users/1
✅ [STEP 1] User validation success

🗺️  STEP 2: Lấy thông tin tour từ Tour Service
   [CALL] GET http://localhost:8082/tours/1
✅ [STEP 2] Tour info retrieved

📅 STEP 3: Tạo booking từ Booking Service
   [CALL] POST http://localhost:8083/bookings
✅ [STEP 3] Booking created

💳 STEP 4: Xử lý thanh toán từ Payment Service
   [CALL] POST http://localhost:8084/payments
   [PAYMENT] ✅ SUCCESS - PAY-ABC123
✅ [STEP 4] Payment processed successfully

🎉 [FINAL] Trả kết quả về Frontend
============================================================
```

#### 5.3 Payment Failure Scenario

Run cùng request nhiều lần, ~20% sẽ thất bại:

```json
{
  "success": false,
  "bookingId": "BK-XYZ789",
  "tourName": "Tour Hà Nội - Hạ Long",
  "quantity": 2,
  "totalPrice": 5000000,
  "paymentStatus": "failed",
  "message": "⚠️ Booking được tạo nhưng thanh toán thất bại"
}
```

---

### Test 6: Frontend UI

#### 6.1 Login Page

- Mở: http://localhost:3000
- Nhập: `user1` / `123456`
- Expected: Redirect to tours list

#### 6.2 Tours List

- Xem danh sách 5 tours
- Verify prices, duration
- Click on tour card

#### 6.3 Booking Form

- Select quantity (1-10)
- Enter email
- Click "Xác nhận đặt tour"
- Expected: Success message với booking ID

#### 6.4 Booking Success Page

- Verify booking details displayed
- Verify payment status
- Try "Đặt tour khác"

---

## Automated Testing

### Unit Tests (Jest)

#### Test User Service

```javascript
// user-service/test/login.test.js
describe("User Service - Login", () => {
  test("should login successfully with correct credentials", async () => {
    const response = await loginUser("user1", "123456");
    expect(response.success).toBe(true);
    expect(response.user.id).toBe(1);
  });

  test("should fail with incorrect password", async () => {
    const response = await loginUser("user1", "wrongpass");
    expect(response.success).toBe(false);
  });
});
```

### Integration Tests

```javascript
// Test entire booking flow
describe("Book Tour Flow", () => {
  test("should complete booking flow successfully", async () => {
    // 1. Login
    const loginRes = await login("user1", "123456");
    expect(loginRes.success).toBe(true);

    // 2. Get tours
    const toursRes = await getTours();
    expect(toursRes.tours.length).toBeGreaterThan(0);

    // 3. Book tour
    const bookRes = await bookTour({
      userId: 1,
      tourId: 1,
      quantity: 2,
      email: "user1@example.com",
    });
    expect(bookRes.success).toBe(true);
    expect(bookRes.bookingId).toBeDefined();
  });
});
```

---

## Performance Testing

### Load Test (Apache JMeter / k6)

#### k6 Script

```javascript
// load-test.js
import http from "k6/http";
import { check } from "k6";

export let options = {
  vus: 10,
  duration: "30s",
};

export default function () {
  let response = http.post("http://localhost:8080/book-tour", {
    userId: 1,
    tourId: 1,
    quantity: 2,
    email: "user@example.com",
  });

  check(response, {
    "status is 200": (r) => r.status === 200,
    "has booking ID": (r) => r.json("bookingId") !== null,
  });
}
```

Run:

```bash
k6 run load-test.js
```

---

## Checklist Testing

### Pre-Deployment Checklist

- [ ] Tất cả 5 services đang chạy
- [ ] Frontend kết nối được Orchestrator
- [ ] User login thành công
- [ ] Danh sách tour hiển thị
- [ ] Có thể tạo booking
- [ ] Payment xử lý được
- [ ] Logs không có error
- [ ] Response time < 5s
- [ ] Không có CORS error
- [ ] Responsive trên mobile

### Regression Testing

Sau mỗi thay đổi, test lại:

1. Login flow
2. Tour viewing
3. Booking flow (success & failure)
4. Payment handling
5. UI rendering

---

## Debugging Tips

### 1. Enable Verbose Logging

```javascript
// Trong Orchestrator
console.log("REQUEST:", JSON.stringify(req.body, null, 2));
console.log("RESPONSE:", JSON.stringify(result.data, null, 2));
```

### 2. Network Trace

```bash
# Windows
netstat -ano | findstr :8080

# Mac/Linux
lsof -i :8080
```

### 3. Check Service Health

```bash
# Health check all services
for port in 8081 8082 8083 8084 8080; do
  echo "Port $port:"
  curl http://localhost:$port/health
done
```

### 4. Browser DevTools

- F12 → Network tab
- Xem request/response details
- Check Content-Type headers

---

## Common Issues & Fixes

| Issue              | Cause                | Fix                    |
| ------------------ | -------------------- | ---------------------- |
| Connection refused | Service not running  | Start service          |
| CORS error         | Missing CORS headers | Enable CORS in Express |
| 404 Not Found      | Wrong endpoint URL   | Check API path         |
| JSON parse error   | Invalid JSON         | Validate JSON format   |
| Timeout            | Service too slow     | Increase timeout       |

---

**Happy Testing! 🧪**
