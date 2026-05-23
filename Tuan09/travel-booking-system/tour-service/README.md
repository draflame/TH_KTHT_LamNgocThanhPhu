# TOUR SERVICE - Người 4

**Quản lý tour**

## Chức năng

- ✅ Lấy danh sách tour (`GET /tours`)
- ✅ Chi tiết tour (`GET /tours/:id`)

## Chạy

```bash
npm install
npm start
```

Port: **8082**

## Test API

### Danh sách tour

```bash
curl http://localhost:8082/tours
```

### Chi tiết tour

```bash
curl http://localhost:8082/tours/1
```

## Tours có sẵn

1. Tour Hà Nội - Hạ Long (2,500,000 VND)
2. Tour Sài Gòn - Cần Thơ (1,500,000 VND)
3. Tour Đà Nẵng - Hội An (3,000,000 VND)
4. Tour Phú Quốc (2,000,000 VND)
5. Tour Sapa - Fansipan (1,800,000 VND)

## Note

- Chỉ có Orchestrator gọi service này
- Không gọi service khác
