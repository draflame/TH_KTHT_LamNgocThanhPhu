# Travel Booking System - Kiến trúc Microservices

Hệ thống đặt tour với kiến trúc **Orchestration-Driven SOA**.

## Cấu trúc Hệ thống

```
┌─────────────────────────────────────────────────┐
│         Frontend (ReactJS)                      │
│      192.168.1.15:3000 - Người 1               │
└────────────────┬────────────────────────────────┘
                 │ (gọi chỉ Orchestrator)
┌────────────────▼────────────────────────────────┐
│    Orchestrator Service (Điều phối)             │
│      192.168.1.10:8080 - Người 2               │
│  (gọi các service khác qua REST API)           │
└────┬──────────────┬───────────────┬─────────────┘
     │              │               │
     ▼              ▼               ▼
┌──────────┐  ┌──────────┐  ┌──────────────┐
│  User    │  │  Tour    │  │  Booking +   │
│ Service  │  │ Service  │  │  Payment     │
│  :8081   │  │  :8082   │  │   :8083-84   │
│ Người 3  │  │ Người 4  │  │  Người 5     │
└──────────┘  └──────────┘  └──────────────┘
```

## Phân công 5 người

### 👤 Người 1: Frontend (ReactJS)

- **Thư mục**: `frontend/`
- **Port**: 192.168.1.15:3000
- **Tính năng**:
  - ✅ Đăng nhập
  - ✅ Xem danh sách tour
  - ✅ Xem chi tiết tour
  - ✅ Đặt tour
- **Quy tắc**: Chỉ gọi **Orchestrator API**

### 🎛️ Người 2: Orchestrator Service

- **Thư mục**: `orchestrator-service/`
- **Port**: 192.168.1.10:8080
- **Chức năng**: Điều phối toàn bộ flow
- **API chính**:
  - `POST /book-tour` - Đặt tour (Flow chính)
- **Flow trong Orchestrator**:
  1. Validate user (gọi User Service)
  2. Lấy thông tin tour (gọi Tour Service)
  3. Tạo booking (gọi Booking Service)
  4. Xử lý thanh toán (gọi Payment Service)
  5. Trả kết quả về Frontend

### 👥 Người 3: User Service

- **Thư mục**: `user-service/`
- **Port**: 192.168.1.11:8081
- **API**:
  - `POST /login` - Đăng nhập
  - `GET /users/{id}` - Lấy thông tin user
  - `POST /users` - Đăng ký (tùy chọn)

### 🗺️ Người 4: Tour Service

- **Thư mục**: `tour-service/`
- **Port**: 192.168.1.12:8082
- **API**:
  - `GET /tours` - Danh sách tour
  - `GET /tours/{id}` - Chi tiết tour

### 💰 Người 5: Booking + Payment Service

- **Thư mục**: `booking-payment-service/`
- **Port**: 192.168.1.13:8083 (Booking), 192.168.1.14:8084 (Payment)
- **API**:
  - **Booking**: `POST /bookings` - Tạo booking
  - **Payment**: `POST /payments` - Xử lý thanh toán (random success/fail)

## Yêu cầu hệ thống

- Node.js v16+
- npm hoặc yarn
- Docker & Docker Compose (tùy chọn)

## Cài đặt và chạy

### Cách 1: Chạy từng service riêng

```bash
# Terminal 1: User Service
cd user-service
npm install
npm start

# Terminal 2: Tour Service
cd tour-service
npm install
npm start

# Terminal 3: Booking + Payment Service
cd booking-payment-service
npm install
npm start

# Terminal 4: Orchestrator Service
cd orchestrator-service
npm install
npm start

# Terminal 5: Frontend
cd frontend
npm install
npm start
```

### Cách 2: Chạy với Docker Compose (tối ưu cho nhiều người)

```bash
docker-compose up -d
```

## Quy tắc kiến trúc

⚠️ **QUAN TRỌNG**:

1. ✅ **Frontend** → chỉ gọi **Orchestrator**
2. ✅ **Orchestrator** → gọi các services khác
3. ❌ **Các services** → không được gọi nhau trực tiếp
4. ✅ Tất cả gọi là **REST API** (HTTP POST/GET)

## Dữ liệu mẫu

### Tours

```json
[
  {
    "id": 1,
    "name": "Tour Hà Nội - Hạ Long",
    "price": 2500000,
    "duration": 3,
    "description": "Tour khám phá Hạ Long 3 ngày"
  },
  {
    "id": 2,
    "name": "Tour Sài Gòn - Cần Thơ",
    "price": 1500000,
    "duration": 2,
    "description": "Tour miện Tây 2 ngày"
  }
]
```

### Users

```json
{
  "id": 1,
  "username": "user1",
  "password": "123456",
  "email": "user1@example.com"
}
```

## Testing

Sau khi chạy tất cả services, test flow đặt tour:

```bash
# 1. Đăng nhập
curl -X POST http://192.168.1.10:8080/book-tour \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "tourId": 1,
    "quantity": 2,
    "email": "user1@example.com"
  }'
```

## Files cần tạo cho mỗi service

Mỗi folder service cần:

- `package.json` - Dependencies
- `src/index.js` - Entry point
- `src/routes/` - API routes
- `.env` (tùy chọn) - Environment variables

## Ghi chú quan trọng

- Tất cả services chạy độc lập, mỗi người 1 service
- Orchestrator là trung tâm, điều phối tất cả
- Frontend không biết về các services khác
- Sử dụng REST API, dễ debug với Postman hoặc Insomnia
- Có thể thêm database (MongoDB/MySQL) sau này

---

**Ngôn ngữ**: Node.js + Express.js  
**Framework Frontend**: React  
**Kiến trúc**: SOA với Orchestrator Pattern  
**Giao tiếp**: REST API
