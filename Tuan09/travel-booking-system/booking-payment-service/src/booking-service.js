/**
 * Booking Service
 * Port: 8083 (Người 5)
 *
 * API:
 * - POST /bookings (Tạo booking)
 */

const express = require("express");
const cors = require("cors");
const bodyParser = require("body-parser");
const { v4: uuidv4 } = require("uuid");

const app = express();
const PORT = process.env.PORT || 8083;

// Middleware
app.use(cors());
app.use(bodyParser.json());

// ✅ Lưu trữ booking
const bookings = [];

// ============================================
// API ENDPOINTS
// ============================================

/**
 * @POST /bookings
 * Tạo booking
 * Request: { userId, tourId, quantity, email, tourPrice }
 * Response: { success, bookingId, message }
 */
app.post("/bookings", (req, res) => {
  console.log("[BOOKING SERVICE] POST /bookings:", req.body);

  const { userId, tourId, quantity, email, tourPrice } = req.body;

  // Validate
  if (!userId || !tourId || !quantity || !email) {
    return res.status(400).json({
      success: false,
      message: "Thiếu thông tin booking",
    });
  }

  // Tạo booking ID
  const bookingId = "BK-" + uuidv4().substring(0, 8).toUpperCase();

  const booking = {
    bookingId,
    userId,
    tourId,
    quantity,
    email,
    totalPrice: tourPrice * quantity,
    status: "confirmed",
    createdAt: new Date().toISOString(),
  };

  bookings.push(booking);

  return res.status(201).json({
    success: true,
    bookingId: booking.bookingId,
    booking: booking,
    message: "Booking tạo thành công",
  });
});

/**
 * @GET /bookings/:id
 * Lấy chi tiết booking
 */
app.get("/bookings/:id", (req, res) => {
  console.log("[BOOKING SERVICE] GET /bookings/:id -", req.params.id);

  const booking = bookings.find((b) => b.bookingId === req.params.id);

  if (!booking) {
    return res.status(404).json({
      success: false,
      message: "Booking không tồn tại",
    });
  }

  return res.json({
    success: true,
    booking,
  });
});

/**
 * @GET /health
 * Health check
 */
app.get("/health", (req, res) => {
  res.json({ status: "Booking Service is running", port: PORT });
});

// ============================================
// Start Server
// ============================================
app.listen(PORT, () => {
  console.log(`\n${"=".repeat(50)}`);
  console.log(`📅 BOOKING SERVICE - Người 5`);
  console.log(`${"=".repeat(50)}`);
  console.log(`✅ Server running on http://localhost:${PORT}`);
  console.log(`\n📋 API Endpoints:`);
  console.log(`   POST /bookings (Tạo booking)`);
  console.log(`   GET  /bookings/:id (Chi tiết booking)`);
  console.log(`   GET  /health (Health check)`);
  console.log(`${"=".repeat(50)}\n`);
});

module.exports = app;
