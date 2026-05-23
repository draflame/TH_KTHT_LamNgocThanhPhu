import React from "react";
import "../styles/BookingSuccess_new.css";

const BookingSuccess = ({ bookingResult, onNewBooking, onLogout }) => {
  const isPaymentSuccess = bookingResult.paymentStatus === "success";

  return (
    <div className="booking-success-container">
      <div className="success-card">
        <div className="success-icon">{isPaymentSuccess ? "✅" : "⚠️"}</div>

        <h2>
          {isPaymentSuccess ? "Đặt Tour Thành Công!" : "Đặt Tour Thất Bại"}
        </h2>

        <p>
          {isPaymentSuccess
            ? "Cảm ơn bạn đã đặt tour. Hãy kiểm tra email của bạn."
            : "Thanh toán không thành công. Vui lòng thử lại."}
        </p>

        <div className="booking-details">
          <h3>Chi Tiết Đơn Đặt</h3>
          <div className="detail-row">
            <strong>Tour:</strong>
            <span>{bookingResult.tourName}</span>
          </div>
          <div className="detail-row">
            <strong>Số người:</strong>
            <span>{bookingResult.quantity}</span>
          </div>
          <div className="detail-row">
            <strong>Tổng tiền:</strong>
            <span>{bookingResult.totalPrice?.toLocaleString("vi-VN")} VND</span>
          </div>
          <div className="detail-row">
            <strong>Thanh toán:</strong>
            <span className={isPaymentSuccess ? "" : ""}>
              {isPaymentSuccess ? "✅ Thành công" : "❌ Thất bại"}
            </span>
          </div>
        </div>

        {bookingResult.bookingId && (
          <div className="booking-id">
            <div className="booking-id-label">Mã Đặt Tour:</div>
            <div className="booking-id-value">{bookingResult.bookingId}</div>
          </div>
        )}

        {isPaymentSuccess && (
          <div className="status-badge status-success">✓ Đã Xác Nhận</div>
        )}

        {!isPaymentSuccess && (
          <div className="status-badge status-failed">
            ✕ Thanh Toán Thất Bại
          </div>
        )}

        <div className="button-group">
          <button className="new-booking-btn" onClick={onNewBooking}>
            Đặt Tour Khác
          </button>
          <button className="logout-btn" onClick={onLogout}>
            Đăng Xuất
          </button>
        </div>
      </div>
    </div>
  );
};

export default BookingSuccess;
