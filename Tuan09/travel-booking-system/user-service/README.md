# USER SERVICE - Người 3

**Quản lý người dùng**

## Chức năng

- ✅ Đăng nhập (`POST /login`)
- ✅ Lấy thông tin user (`GET /users/:id`)
- ✅ Tạo user mới (`POST /users`)

## Chạy

```bash
npm install
npm start
```

Port: **8081**

## Test API

### Đăng nhập

```bash
curl -X POST http://localhost:8081/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user1",
    "password": "123456"
  }'
```

### Lấy user info

```bash
curl http://localhost:8081/users/1
```

### Tạo user mới

```bash
curl -X POST http://localhost:8081/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user4",
    "password": "newpass",
    "email": "user4@example.com",
    "fullName": "Người mới"
  }'
```

## Note

- Chỉ có Orchestrator gọi service này
- Không gọi service khác
