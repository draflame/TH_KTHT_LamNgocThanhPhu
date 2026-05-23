import React, { useState } from "react";
import { orchestratorAPI } from "../services/api";
import "../styles/BookingForm.css";

const BookingForm = ({ tour, user, onBookingComplete, onCancel }) => {
  const [quantity, setQuantity] = useState(1);
  const [email, setEmail] = useState(user?.email || "");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const totalPrice = tour.price * quantity;

  const handleBook = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage("");
    setError("");

    try {
      const response = await orchestratorAPI.bookTour(
        user.id,
        tour.id,
        quantity,
        email,
      );

      if (response.success) {
        setMessage(`✅ Đặt tour thành công! Mã booking: ${response.bookingId}`);
        setTimeout(() => {
          onBookingComplete(response);
        }, 2000);
      } else {
        setError(response.message || "Đặt tour thất bại");
      }
    } catch (err) {
      setError(err.message || "Lỗi xử lý booking");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="booking-form-container">
      <div className="booking-card">
        <h2>🎫 Chi tiết đặt tour</h2>
        <div className="tour-info">
          <h3>{tour.name}</h3>
          <p>Giá: {tour.price.toLocaleString()} VND / người</p>
          <p>Thời gian: {tour.duration} ngày</p>
        </div>

        <form onSubmit={handleBook}>
          <div className="form-group">
            <label>Số lượng người</label>
            <input
              type="number"
              min="1"
              max="10"
              value={quantity}
              onChange={(e) => setQuantity(parseInt(e.target.value))}
              required
            />
          </div>

          <div className="form-group">
            <label>Email</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          <div className="total-price">
            <strong>Tổng tiền: {totalPrice.toLocaleString()} VND</strong>
          </div>

          {error && <div className="error-message">{error}</div>}
          {message && <div className="success-message">{message}</div>}

          <div className="button-group">
            <button type="submit" disabled={loading}>
              {loading ? "Đang xử lý..." : "Xác nhận đặt tour"}
            </button>
            <button type="button" onClick={onCancel} className="cancel-btn">
              Hủy
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default BookingForm;
