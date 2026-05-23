/**
 * User Service - Quản lý người dùng
 * Port: 8081 (Người 3)
 *
 * API:
 * - POST /login (Đăng nhập)
 * - GET /users/{id} (Lấy thông tin user)
 */

const express = require("express");
const cors = require("cors");
const bodyParser = require("body-parser");
const { v4: uuidv4 } = require("uuid");

const app = express();
const PORT = process.env.PORT || 8081;

// Middleware
app.use(cors());
app.use(bodyParser.json());

// ✅ Mock dữ liệu user
const users = [
  {
    id: 1,
    username: "user1",
    password: "123456",
    email: "user1@example.com",
    fullName: "Nguyễn Văn A",
  },
  {
    id: 2,
    username: "user2",
    password: "password123",
    email: "user2@example.com",
    fullName: "Trần Thị B",
  },
  {
    id: 3,
    username: "user3",
    password: "pass456",
    email: "user3@example.com",
    fullName: "Lê Văn C",
  },
];

// ============================================
// API ENDPOINTS
// ============================================

/**
 * @POST /login
 * Đăng nhập người dùng
 * Request: { username, password }
 * Response: { success, user, message }
 */
app.post("/login", (req, res) => {
  console.log("[USER SERVICE] POST /login:", req.body);

  const { username, password } = req.body;

  // Validate
  if (!username || !password) {
    return res.status(400).json({
      success: false,
      message: "Username và password là bắt buộc",
    });
  }

  // Tìm user
  const user = users.find(
    (u) => u.username === username && u.password === password,
  );

  if (!user) {
    return res.status(401).json({
      success: false,
      message: "Username hoặc password không chính xác",
    });
  }

  // Trả về user info (không bao gồm password)
  const { password: _, ...userInfo } = user;

  return res.json({
    success: true,
    user: userInfo,
    message: "Đăng nhập thành công",
  });
});

/**
 * @GET /users/:id
 * Lấy thông tin user theo ID
 * Response: { success, user, message }
 */
app.get("/users/:id", (req, res) => {
  console.log("[USER SERVICE] GET /users/:id -", req.params.id);

  const { id } = req.params;
  const user = users.find((u) => u.id === parseInt(id));

  if (!user) {
    return res.status(404).json({
      success: false,
      message: "User không tồn tại",
    });
  }

  const { password: _, ...userInfo } = user;

  return res.json({
    success: true,
    user: userInfo,
  });
});

/**
 * @POST /users
 * Tạo user mới (tùy chọn)
 */
app.post("/users", (req, res) => {
  console.log("[USER SERVICE] POST /users:", req.body);

  const { username, password, email, fullName } = req.body;

  if (!username || !password || !email) {
    return res.status(400).json({
      success: false,
      message: "Username, password, email là bắt buộc",
    });
  }

  // Kiểm tra username đã tồn tại
  if (users.find((u) => u.username === username)) {
    return res.status(400).json({
      success: false,
      message: "Username đã được sử dụng",
    });
  }

  const newUser = {
    id: users.length + 1,
    username,
    password,
    email,
    fullName: fullName || "",
  };

  users.push(newUser);

  const { password: _, ...userInfo } = newUser;

  return res.status(201).json({
    success: true,
    user: userInfo,
    message: "Tạo user thành công",
  });
});

/**
 * @GET /health
 * Health check
 */
app.get("/health", (req, res) => {
  res.json({ status: "User Service is running", port: PORT });
});

// ============================================
// Start Server
// ============================================
app.listen(PORT, () => {
  console.log(`\n${"=".repeat(50)}`);
  console.log(`👥 USER SERVICE - Người 3`);
  console.log(`${"=".repeat(50)}`);
  console.log(`✅ Server running on http://localhost:${PORT}`);
  console.log(`\n📋 API Endpoints:`);
  console.log(`   POST /login (Đăng nhập)`);
  console.log(`   GET  /users/:id (Lấy user)`);
  console.log(`   POST /users (Tạo user mới)`);
  console.log(`   GET  /health (Health check)`);
  console.log(`\n💡 Demo:`);
  console.log(`   username: user1`);
  console.log(`   password: 123456`);
  console.log(`${"=".repeat(50)}\n`);
});

module.exports = app;
