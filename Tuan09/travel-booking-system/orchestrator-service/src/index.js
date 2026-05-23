/**
 * ⭐ ORCHESTRATOR SERVICE ⭐
 * Port: 8080 (Người 2)
 *
 * ✅ Điều phối toàn bộ flow:
 * 1. Frontend → Orchestrator
 * 2. Orchestrator → gọi các services khác
 * 3. Trả kết quả về Frontend
 *
 * ✅ Các services KHÔNG gọi nhau trực tiếp
 * ✅ Tất cả đều là REST call
 *
 * API:
 * - POST /login (Forward to User Service)
 * - GET  /tours (Forward to Tour Service)
 * - GET  /tours/:id (Forward to Tour Service)
 * - POST /book-tour (MAIN FLOW - Orchestration)
 */

const express = require("express");
const cors = require("cors");
const bodyParser = require("body-parser");
const axios = require("axios");

const app = express();
const PORT = process.env.PORT || 8080;

// Middleware
app.use(cors());
app.use(bodyParser.json());

// ============================================
// Service URLs
// ============================================
const SERVICE_URLS = {
  USER: process.env.USER_SERVICE_URL || "http://localhost:8081",
  TOUR: process.env.TOUR_SERVICE_URL || "http://localhost:8082",
  BOOKING: process.env.BOOKING_SERVICE_URL || "http://localhost:8083",
  PAYMENT: process.env.PAYMENT_SERVICE_URL || "http://localhost:8084",
};

// ============================================
// Helper Functions
// ============================================

/**
 * Gọi các services khác thông qua REST API
 */
const callService = async (method, url, data = null) => {
  try {
    console.log(`   [CALL] ${method} ${url}`);
    const config = { method, url };
    if (data) config.data = data;
    const response = await axios(config);
    return { success: true, data: response.data };
  } catch (error) {
    console.error(`   [ERROR] ${url}`, error.message);
    return {
      success: false,
      error: error.response?.data?.message || error.message,
    };
  }
};

// ============================================
// API ENDPOINTS - PASS THROUGH
// ============================================

/**
 * @POST /login
 * Đăng nhập - Forward to User Service
 */
app.post("/login", async (req, res) => {
  console.log("\n[ORCHESTRATOR] POST /login");
  console.log("Request:", req.body);

  const result = await callService(
    "POST",
    `${SERVICE_URLS.USER}/login`,
    req.body,
  );

  if (!result.success) {
    return res.status(401).json({
      success: false,
      message: result.error,
    });
  }

  return res.json(result.data);
});

/**
 * @GET /tours
 * Danh sách tour - Forward to Tour Service
 */
app.get("/tours", async (req, res) => {
  console.log("\n[ORCHESTRATOR] GET /tours");

  const result = await callService("GET", `${SERVICE_URLS.TOUR}/tours`);

  if (!result.success) {
    return res.status(500).json({
      success: false,
      message: result.error,
    });
  }

  return res.json(result.data);
});

/**
 * @GET /tours/:id
 * Chi tiết tour - Forward to Tour Service
 */
app.get("/tours/:id", async (req, res) => {
  console.log("\n[ORCHESTRATOR] GET /tours/:id -", req.params.id);

  const result = await callService(
    "GET",
    `${SERVICE_URLS.TOUR}/tours/${req.params.id}`,
  );

  if (!result.success) {
    return res.status(500).json({
      success: false,
      message: result.error,
    });
  }

  return res.json(result.data);
});

// ============================================
// ⭐ MAIN ORCHESTRATION FLOW
// ============================================

/**
 * @POST /book-tour
 *
 * ⭐ FLOW CHÍNH (Orchestration Pattern):
 *
 * 1. Validate user (User Service)
 * 2. Lấy thông tin tour (Tour Service)
 * 3. Tạo booking (Booking Service)
 * 4. Xử lý thanh toán (Payment Service)
 * 5. Trả kết quả về Frontend
 *
 * Nếu bất kỳ bước nào thất bại, rollback
 */
app.post("/book-tour", async (req, res) => {
  console.log("\n" + "=".repeat(60));
  console.log("⭐ [ORCHESTRATOR] POST /book-tour - MAIN FLOW START");
  console.log("=".repeat(60));
  console.log("Request:", req.body);

  const { userId, tourId, quantity, email } = req.body;

  // Validate input
  if (!userId || !tourId || !quantity || !email) {
    console.log("❌ [VALIDATION] Thiếu thông tin");
    return res.status(400).json({
      success: false,
      message: "Thiếu thông tin: userId, tourId, quantity, email",
    });
  }

  // ============================================
  // STEP 1: Validate User
  // ============================================
  console.log("\n📝 STEP 1: Validate user từ User Service");
  const userResult = await callService(
    "GET",
    `${SERVICE_URLS.USER}/users/${userId}`,
  );

  if (!userResult.success) {
    console.log("❌ [STEP 1] User validation failed");
    return res.status(401).json({
      success: false,
      message: "User không tồn tại",
    });
  }
  console.log("✅ [STEP 1] User validation success");
  const user = userResult.data.user;

  // ============================================
  // STEP 2: Get Tour Info
  // ============================================
  console.log("\n🗺️  STEP 2: Lấy thông tin tour từ Tour Service");
  const tourResult = await callService(
    "GET",
    `${SERVICE_URLS.TOUR}/tours/${tourId}`,
  );

  if (!tourResult.success) {
    console.log("❌ [STEP 2] Tour not found");
    return res.status(404).json({
      success: false,
      message: "Tour không tồn tại",
    });
  }
  console.log("✅ [STEP 2] Tour info retrieved");
  const tour = tourResult.data.tour;

  // ============================================
  // STEP 3: Create Booking
  // ============================================
  console.log("\n📅 STEP 3: Tạo booking từ Booking Service");
  const bookingPayload = {
    userId,
    tourId,
    quantity,
    email,
    tourPrice: tour.price,
  };

  const bookingResult = await callService(
    "POST",
    `${SERVICE_URLS.BOOKING}/bookings`,
    bookingPayload,
  );

  if (!bookingResult.success) {
    console.log("❌ [STEP 3] Booking creation failed");
    return res.status(500).json({
      success: false,
      message: "Không thể tạo booking",
    });
  }
  console.log("✅ [STEP 3] Booking created");
  const booking = bookingResult.data.booking;
  const bookingId = bookingResult.data.bookingId;

  // ============================================
  // STEP 4: Process Payment
  // ============================================
  console.log("\n💳 STEP 4: Xử lý thanh toán từ Payment Service");
  const totalPrice = tour.price * quantity;
  const paymentPayload = {
    bookingId,
    amount: totalPrice,
    email,
  };

  const paymentResult = await callService(
    "POST",
    `${SERVICE_URLS.PAYMENT}/payments`,
    paymentPayload,
  );

  let paymentSuccess = paymentResult.success;
  let paymentStatus = paymentSuccess ? "success" : "failed";

  if (paymentSuccess) {
    console.log("✅ [STEP 4] Payment processed successfully");
  } else {
    console.log("❌ [STEP 4] Payment failed:", paymentResult.error);
    // ⚠️ Booking vẫn được tạo nhưng payment thất bại
    // Trong thực tế có thể implement rollback
  }

  // ============================================
  // STEP 5: Return Result
  // ============================================
  console.log("\n🎉 [FINAL] Trả kết quả về Frontend");
  console.log("=".repeat(60) + "\n");

  return res.json({
    success: paymentSuccess,
    bookingId: bookingId,
    userId: userId,
    tourName: tour.name,
    quantity: quantity,
    totalPrice: totalPrice,
    email: email,
    paymentStatus: paymentStatus,
    message: paymentSuccess
      ? "✅ Đặt tour thành công!"
      : "⚠️ Booking được tạo nhưng thanh toán thất bại. Vui lòng thử lại",
  });
});

/**
 * @GET /health
 * Health check
 */
app.get("/health", (req, res) => {
  res.json({
    status: "Orchestrator Service is running",
    port: PORT,
    services: SERVICE_URLS,
  });
});

/**
 * @GET /
 * Welcome page
 */
app.get("/", (req, res) => {
  res.json({
    service: "Travel Booking System - Orchestrator",
    port: PORT,
    description: "Điều phối toàn bộ flow đặt tour",
    endpoints: {
      login: "POST /login",
      tours: "GET /tours",
      tourDetail: "GET /tours/:id",
      bookTour: "POST /book-tour (Main Flow)",
      health: "GET /health",
    },
    flow: "Frontend → Orchestrator → Services",
  });
});

// ============================================
// Error Handler
// ============================================
app.use((err, req, res, next) => {
  console.error("[ERROR]", err);
  res.status(500).json({
    success: false,
    message: "Server error",
  });
});

// ============================================
// Start Server
// ============================================
app.listen(PORT, () => {
  console.log(`\n${"=".repeat(60)}`);
  console.log(`⭐ ORCHESTRATOR SERVICE - Người 2`);
  console.log(`${"=".repeat(60)}`);
  console.log(`✅ Server running on http://localhost:${PORT}`);
  console.log(`\n📋 Service URLs:`);
  console.log(`   User Service:    ${SERVICE_URLS.USER}`);
  console.log(`   Tour Service:    ${SERVICE_URLS.TOUR}`);
  console.log(`   Booking Service: ${SERVICE_URLS.BOOKING}`);
  console.log(`   Payment Service: ${SERVICE_URLS.PAYMENT}`);
  console.log(`\n📝 API Endpoints:`);
  console.log(`   POST /login (Đăng nhập)`);
  console.log(`   GET  /tours (Danh sách tour)`);
  console.log(`   GET  /tours/:id (Chi tiết tour)`);
  console.log(`   POST /book-tour (⭐ MAIN FLOW - Đặt tour)`);
  console.log(`   GET  /health (Health check)`);
  console.log(`\n🔄 Flow Orchestration:`);
  console.log(`   1. Validate user (User Service)`);
  console.log(`   2. Get tour info (Tour Service)`);
  console.log(`   3. Create booking (Booking Service)`);
  console.log(`   4. Process payment (Payment Service)`);
  console.log(`   5. Return result to Frontend`);
  console.log(`${"=".repeat(60)}\n`);
});

module.exports = app;
