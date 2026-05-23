/**
 * Payment Service
 * Port: 8084 (Người 5)
 *
 * API:
 * - POST /payments (Xử lý thanh toán - random success/fail)
 */

const express = require("express");
const cors = require("cors");
const bodyParser = require("body-parser");
const { v4: uuidv4 } = require("uuid");

const app = express();
const PORT = process.env.PORT || 8084;

// Middleware
app.use(cors());
app.use(bodyParser.json());

// ✅ Lưu trữ payment
const payments = [];

// ============================================
// API ENDPOINTS
// ============================================

/**
 * @POST /payments
 * Xử lý thanh toán
 * Request: { bookingId, amount, email }
 * Response: { success, paymentId, status, message }
 *
 * ⚠️ Random success/fail: 80% thành công, 20% thất bại
 */
app.post("/payments", (req, res) => {
  console.log("[PAYMENT SERVICE] POST /payments:", req.body);

  const { bookingId, amount, email } = req.body;

  // Validate
  if (!bookingId || !amount || !email) {
    return res.status(400).json({
      success: false,
      message: "Thiếu thông tin thanh toán",
    });
  }

  // ✅ Random success/fail: 80% thành công
  const isSuccess = Math.random() > 0.2; // 80% success

  const paymentId = "PAY-" + uuidv4().substring(0, 8).toUpperCase();

  const payment = {
    paymentId,
    bookingId,
    amount,
    email,
    status: isSuccess ? "success" : "failed",
    createdAt: new Date().toISOString(),
  };

  payments.push(payment);

  console.log(
    `   [PAYMENT] ${isSuccess ? "✅ SUCCESS" : "❌ FAILED"} - ${paymentId}`,
  );

  if (isSuccess) {
    return res.status(200).json({
      success: true,
      paymentId: payment.paymentId,
      status: "success",
      amount: amount,
      message: "Thanh toán thành công",
    });
  } else {
    return res.status(400).json({
      success: false,
      paymentId: payment.paymentId,
      status: "failed",
      message: "Thanh toán thất bại, vui lòng thử lại",
    });
  }
});

/**
 * @GET /payments/:id
 * Lấy chi tiết payment
 */
app.get("/payments/:id", (req, res) => {
  console.log("[PAYMENT SERVICE] GET /payments/:id -", req.params.id);

  const payment = payments.find((p) => p.paymentId === req.params.id);

  if (!payment) {
    return res.status(404).json({
      success: false,
      message: "Payment không tồn tại",
    });
  }

  return res.json({
    success: true,
    payment,
  });
});

/**
 * @GET /health
 * Health check
 */
app.get("/health", (req, res) => {
  res.json({ status: "Payment Service is running", port: PORT });
});

// ============================================
// Start Server
// ============================================
app.listen(PORT, () => {
  console.log(`\n${"=".repeat(50)}`);
  console.log(`💳 PAYMENT SERVICE - Người 5`);
  console.log(`${"=".repeat(50)}`);
  console.log(`✅ Server running on http://localhost:${PORT}`);
  console.log(`\n📋 API Endpoints:`);
  console.log(`   POST /payments (Thanh toán - 80% success)`);
  console.log(`   GET  /payments/:id (Chi tiết payment)`);
  console.log(`   GET  /health (Health check)`);
  console.log(`${"=".repeat(50)}\n`);
});

module.exports = app;
