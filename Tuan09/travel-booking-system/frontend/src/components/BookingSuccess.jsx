import React from "react";
import "../styles/BookingSuccess.css";

const BookingSuccess = ({ booking, user, onNewBooking }) => {
  return (
    <div className="success-container">
      <div className="success-card">
        <div className="success-icon">✅</div>
        <h2>Đặt tour thành công!</h2>

        <div className="booking-details">
          <div className="detail-item">
            <span className="label">Mã booking:</span>
            <span className="value">{booking.bookingId}</span>
          </div>
          <div className="detail-item">
            <span className="label">Tên khách:</span>
            <span className="value">{user.username}</span>
          </div>
          <div className="detail-item">
            <span className="label">Email:</span>
            <span className="value">{booking.email}</span>
          </div>
          <div className="detail-item">
            <span className="label">Tổng giá:</span>
            <span className="value">
              {booking.totalPrice?.toLocaleString()} VND
            </span>
          </div>
          <div className="detail-item">
            <span className="label">Trạng thái thanh toán:</span>
            <span className={`value ${booking.paymentStatus}`}>
              {booking.paymentStatus === "success"
                ? "✅ Thành công"
                : "⏳ Chờ xác nhận"}
            </span>
          </div>
        </div>

        <p className="notice">
          📧 Một email xác nhận sẽ được gửi tới địa chỉ của bạn
        </p>

        <button onClick={onNewBooking} className="new-booking-btn">
          Đặt tour khác
        </button>
      </div>
    </div>
  );
};

export default BookingSuccess;
