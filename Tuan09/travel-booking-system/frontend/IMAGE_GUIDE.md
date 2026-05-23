# 🖼️ Hướng Dẫn Thay Thế Ảnh Tour

## Cách Sử Dụng Ảnh PNG Local

### Bước 1: Chuẩn Bị Ảnh

- Tạo 5 file ảnh với kích thước: **500x300px** (hoặc tỷ lệ 16:9)
- Định dạng: `.jpg` hoặc `.png`
- Tên file:
  - `tour-1.jpg` - Hà Nội - Hạ Long
  - `tour-2.jpg` - Sài Gòn - Cần Thơ
  - `tour-3.jpg` - Đà Nẵng - Hội An
  - `tour-4.jpg` - Phú Quốc
  - `tour-5.jpg` - Sapa - Fansipan

### Bước 2: Copy Ảnh Vào Folder

```
travel-booking-system/
└── frontend/
    └── public/
        └── images/
            ├── tour-1.jpg
            ├── tour-2.jpg
            ├── tour-3.jpg
            ├── tour-4.jpg
            └── tour-5.jpg
```

### Bước 3: Cập Nhật URL Trong Code

Mở file `frontend/src/components/TourList_new.jsx` và thay đổi:

```javascript
const tourImages = {
  1: "/images/tour-1.jpg",
  2: "/images/tour-2.jpg",
  3: "/images/tour-3.jpg",
  4: "/images/tour-4.jpg",
  5: "/images/tour-5.jpg",
};
```

### ✅ Hiện Tại

Đang dùng **Unsplash URLs** (online) - không cần copy file local

### 🖼️ Kích Thước Khuyến Nghị

- Width: 500px
- Height: 300px
- Format: JPG hoặc PNG
- Compression: Optimize với Tiny PNG hoặc ImageMin

### 💡 Tip

- Hình ảnh sẽ hiển thị trong grid responsive (3 cột desktop, 1 cột mobile)
- CSS tự động crop ảnh với `object-fit: cover`
- Được apply shadow và hover animation

---

**Current State**: Grid layout ✅ | Remote URLs ✅ | Ready for local images 📁
