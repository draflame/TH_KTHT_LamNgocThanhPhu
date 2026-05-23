/**
 * Tour Service - Quản lý tour
 * Port: 8082 (Người 4)
 *
 * API:
 * - GET /tours (Danh sách tour)
 * - GET /tours/:id (Chi tiết tour)
 */

const express = require("express");
const cors = require("cors");
const bodyParser = require("body-parser");

const app = express();
const PORT = process.env.PORT || 8082;

// Middleware
app.use(cors());
app.use(bodyParser.json());

// ✅ Mock dữ liệu tour
const tours = [
  {
    id: 1,
    name: "Tour Hà Nội - Hạ Long",
    price: 2500000,
    duration: 3,
    description: "Khám phá Hạ Long 3 ngày 2 đêm với các hoạt động tham quan",
    location: "Quảng Ninh",
    image: "🚢",
    highlights: [
      "Du thuyền trên vịnh Hạ Long",
      "Thăm hang động Sủng Sốt",
      "Chèo kayak qua các đảo",
      "Tắm biển",
    ],
  },
  {
    id: 2,
    name: "Tour Sài Gòn - Cần Thơ",
    price: 1500000,
    duration: 2,
    description: "Khám phá miền Tây Sông Cửu Long 2 ngày 1 đêm",
    location: "Cần Thơ",
    image: "🚤",
    highlights: [
      "Thăm chợ nổi Cái Bè",
      "Chèo thuyền trên sông",
      "Thăm vườn trái cây",
      "Ăn đặc sản miền Tây",
    ],
  },
  {
    id: 3,
    name: "Tour Đà Nẵng - Hội An",
    price: 3000000,
    duration: 4,
    description: "Du lịch Đà Nẵng - Hội An 4 ngày 3 đêm",
    location: "Đà Nẵng",
    image: "🏖️",
    highlights: [
      "Thăm bán đảo Sơn Trà",
      "Phố cổ Hội An",
      "Bãi biển Mỹ Khe",
      "Cầu Vàng Bà Nà",
    ],
  },
  {
    id: 4,
    name: "Tour Phú Quốc",
    price: 2000000,
    duration: 3,
    description: "Đảo ngọc Phú Quốc 3 ngày 2 đêm",
    location: "Kiên Giang",
    image: "🏝️",
    highlights: [
      "Bãi biển Dương Đông",
      "Vườn ngọc trai",
      "Câu cá biển",
      "Tắm biển",
    ],
  },
  {
    id: 5,
    name: "Tour Sapa - Fansipan",
    price: 1800000,
    duration: 3,
    description: "Chinh phục Fansipan tại Sapa 3 ngày 2 đêm",
    location: "Lào Cai",
    image: "⛰️",
    highlights: [
      "Trạm dừng Fansipan",
      "Thị trấn Sapa cổ",
      "Trekking",
      "Gặp gỡ dân tộc",
    ],
  },
];

// ============================================
// API ENDPOINTS
// ============================================

/**
 * @GET /tours
 * Lấy danh sách tất cả tour
 * Response: { tours } hoặc [ tour, ... ]
 */
app.get("/tours", (req, res) => {
  console.log("[TOUR SERVICE] GET /tours");

  return res.json({
    success: true,
    tours: tours,
    total: tours.length,
  });
});

/**
 * @GET /tours/:id
 * Lấy chi tiết tour theo ID
 * Response: { success, tour, message }
 */
app.get("/tours/:id", (req, res) => {
  console.log("[TOUR SERVICE] GET /tours/:id -", req.params.id);

  const { id } = req.params;
  const tour = tours.find((t) => t.id === parseInt(id));

  if (!tour) {
    return res.status(404).json({
      success: false,
      message: "Tour không tồn tại",
    });
  }

  return res.json({
    success: true,
    tour: tour,
  });
});

/**
 * @GET /health
 * Health check
 */
app.get("/health", (req, res) => {
  res.json({ status: "Tour Service is running", port: PORT });
});

// ============================================
// Start Server
// ============================================
app.listen(PORT, () => {
  console.log(`\n${"=".repeat(50)}`);
  console.log(`🗺️  TOUR SERVICE - Người 4`);
  console.log(`${"=".repeat(50)}`);
  console.log(`✅ Server running on http://localhost:${PORT}`);
  console.log(`\n📋 API Endpoints:`);
  console.log(`   GET  /tours (Danh sách tour)`);
  console.log(`   GET  /tours/:id (Chi tiết tour)`);
  console.log(`   GET  /health (Health check)`);
  console.log(`\n💡 Demo:`);
  console.log(`   Có ${tours.length} tour sẵn sàng`);
  console.log(`${"=".repeat(50)}\n`);
});

module.exports = app;
