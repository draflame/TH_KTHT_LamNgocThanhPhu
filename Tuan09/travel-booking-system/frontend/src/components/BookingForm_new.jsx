import React, { useState } from "react";
import { orchestratorAPI } from "../services/api";
import "../styles/BookingForm_new.css";

const BookingForm = ({ tour, user, onBookingComplete, onBack }) => {
  const [quantity, setQuantity] = useState(1);
  const [email, setEmail] = useState(user?.email || "");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const tourImages = {
    1: "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 400 300'%3E%3Crect width='400' height='300' fill='%231e90ff'/%3E%3Ccircle cx='200' cy='80' r='40' fill='%23FFD700'/%3E%3Cpath d='M 50 200 Q 100 150 150 180 T 250 170 Q 300 160 350 200' stroke='%238B4513' stroke-width='3' fill='none'/%3E%3Crect x='60' y='200' width='40' height='80' fill='%238B7355'/%3E%3Crect x='280' y='210' width='35' height='70' fill='%238B7355'/%3E%3Cpath d='M 60 200 L 50 160 L 70 200' fill='%234CAF50'/%3E%3Cpath d='M 310 210 L 298 170 L 322 210' fill='%234CAF50'/%3E%3C/svg%3E",
    2: "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 400 300'%3E%3Cdefs%3E%3ClinearGradient id='sky' x1='0%25' y1='0%25' x2='0%25' y2='100%25'%3E%3Cstop offset='0%25' style='stop-color:%231e90ff;stop-opacity:1' /%3E%3Cstop offset='100%25' style='stop-color:%2387CEEB;stop-opacity:1' /%3E%3C/linearGradient%3E%3C/defs%3E%3Crect width='400' height='300' fill='url(%23sky)'/%3E%3Crect y='200' width='400' height='100' fill='%2390EE90'/%3E%3Ccircle cx='100' cy='100' r='40' fill='%23FFD700'/%3E%3Crect x='150' y='100' width='50' height='100' fill='%238B4513'/%3E%3Crect x='160' y='120' width='12' height='15' fill='%23FFD700'/%3E%3Crect x='180' y='120' width='12' height='15' fill='%23FFD700'/%3E%3Cpolygon points='200,80 220,110 180,110' fill='%23FF6347'/%3E%3C/svg%3E",
    3: "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 400 300'%3E%3Crect width='400' height='200' fill='%231e90ff'/%3E%3Crect y='200' width='400' height='100' fill='%23F4A460'/%3E%3Ccircle cx='330' cy='80' r='35' fill='%23FFD700'/%3E%3Cpath d='M 0 200 Q 50 150 100 180 T 200 160 T 300 190 T 400 200' stroke='%23008080' stroke-width='2' fill='none'/%3E%3Ccircle cx='80' cy='200' r='15' fill='%23228B22'/%3E%3Ccircle cx='180' cy='195' r='18' fill='%23228B22'/%3E%3Ccircle cx='280' cy='205' r='16' fill='%23228B22'/%3E%3C/svg%3E",
    4: "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 400 300'%3E%3Crect width='400' height='150' fill='%2387CEEB'/%3E%3Crect y='150' width='400' height='150' fill='%231e90ff'/%3E%3Ccircle cx='350' cy='50' r='40' fill='%23FFD700'/%3E%3Cpath d='M 0 150 Q 100 130 200 150 Q 300 160 400 150' stroke='%23F4A460' stroke-width='3' fill='none'/%3E%3Cpolygon points='50,150 60,120 70,150' fill='%232F4F4F'/%3E%3Cpolygon points='150,150 165,110 180,150' fill='%232F4F4F'/%3E%3Cpolygon points='280,150 300,100 320,150' fill='%232F4F4F'/%3E%3Ccircle cx='100' cy='200' r='12' fill='%23FFD700'/%3E%3Ccircle cx='250' cy='220' r='10' fill='%23FFD700'/%3E%3Ccircle cx='350' cy='210' r='14' fill='%23FFD700'/%3E%3C/svg%3E",
    5: "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 400 300'%3E%3Crect width='400' height='300' fill='%238A9BC3'/%3E%3Cpolygon points='80,240 150,80 220,240' fill='%23696969'/%3E%3Cpolygon points='200,260 280,120 360,260' fill='%23808080'/%3E%3Ccircle cx='200' cy='100' r='50' fill='%23E8E8E8'/%3E%3Cpath d='M 0 240 L 400 240' stroke='%23FFFFFF' stroke-width='1' stroke-dasharray='5,5'/%3E%3Cpath d='M 150 80 L 200 100 L 250 80' stroke='%23FFFFFF' stroke-width='2' fill='none'/%3E%3C/svg%3E",
  };

  const totalPrice = tour.price * quantity;

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      const bookingData = {
        userId: user.id,
        tourId: tour.id,
        quantity: parseInt(quantity),
        email: email,
      };

      const response = await orchestratorAPI.bookTour(bookingData);

      if (response.success) {
        onBookingComplete(response);
      } else {
        setError(response.message || "Đặt tour thất bại. Vui lòng thử lại.");
      }
    } catch (err) {
      setError(err.message || "Lỗi khi đặt tour. Vui lòng thử lại.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="booking-form-container">
      <div className="booking-form-card">
        <div className="booking-image-section">
          <img
            src={tourImages[tour.id]}
            alt={tour.name}
            className="booking-image"
          />
        </div>

        <div className="booking-form-section">
          <div className="booking-header">
            <h2>Đặt Tour</h2>
            <p style={{ color: "#666", marginTop: "0.5rem" }}>
              Hoàn thiện thông tin để book tour yêu thích
            </p>
          </div>

          <div className="tour-details">
            <p>
              <strong>Tour:</strong> {tour.name}
            </p>
            <p>
              <strong>Thời gian:</strong> {tour.duration} ngày
            </p>
            <p>
              <strong>Giá/người:</strong> {tour.price.toLocaleString("vi-VN")}{" "}
              VND
            </p>
          </div>

          {error && <div className="error-message">{error}</div>}

          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label>Email</label>
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="Nhập email của bạn"
                required
              />
            </div>

            <div className="quantity-group">
              <div className="quantity-input">
                <label>Số lượng người (1-10)</label>
                <input
                  type="number"
                  min="1"
                  max="10"
                  value={quantity}
                  onChange={(e) => setQuantity(Math.max(1, e.target.value))}
                  required
                />
              </div>
            </div>

            <div className="price-info">
              <div className="price-row">
                <span>Giá/người:</span>
                <span>{tour.price.toLocaleString("vi-VN")} VND</span>
              </div>
              <div className="price-row">
                <span>Số người:</span>
                <span>{quantity}</span>
              </div>
              <div className="price-row">
                <span>Tổng cộng:</span>
                <span>{totalPrice.toLocaleString("vi-VN")} VND</span>
              </div>
            </div>

            <div className="button-group">
              <button type="button" className="back-btn" onClick={onBack}>
                Quay Lại
              </button>
              <button type="submit" className="book-btn" disabled={loading}>
                {loading ? "Đang xử lý..." : "Hoàn Tất Đặt Tour"}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default BookingForm;
