import React, { useState } from "react";
import { orchestratorAPI } from "../services/api";
import "../styles/Login.css";

const Login = ({ onLoginSuccess }) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    try {
      const response = await orchestratorAPI.login(username, password);
      if (response.success) {
        // Lưu user info vào localStorage
        localStorage.setItem("user", JSON.stringify(response.user));
        onLoginSuccess(response.user);
      } else {
        setError(response.message || "Đăng nhập thất bại");
      }
    } catch (err) {
      setError(err.message || "Lỗi kết nối với server");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <h1>🌍 Travel Booking System</h1>
        <form onSubmit={handleLogin}>
          <div className="form-group">
            <label>Tên đăng nhập</label>
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="Nhập username"
              required
            />
          </div>
          <div className="form-group">
            <label>Mật khẩu</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Nhập password"
              required
            />
          </div>
          {error && <div className="error-message">{error}</div>}
          <button type="submit" disabled={loading}>
            {loading ? "Đang đăng nhập..." : "Đăng nhập"}
          </button>
        </form>
        <p className="demo-info">💡 Demo: username: user1, password: 123456</p>
      </div>
    </div>
  );
};

export default Login;
