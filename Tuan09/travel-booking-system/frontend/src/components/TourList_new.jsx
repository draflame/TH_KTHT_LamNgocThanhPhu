import React, { useState, useEffect } from "react";
import { orchestratorAPI } from "../services/api";
import "../styles/TourList_new.css";

const TourList = ({ onSelectTour, onLogout }) => {
  const [tours, setTours] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [user, setUser] = useState(null);

  useEffect(() => {
    const savedUser = localStorage.getItem("user");
    if (savedUser) {
      setUser(JSON.parse(savedUser));
    }
    fetchTours();
  }, []);

  const fetchTours = async () => {
    try {
      setLoading(true);
      const response = await orchestratorAPI.getTours();
      setTours(response.tours || []);
    } catch (err) {
      setError("Không thể tải danh sách tour. Vui lòng thử lại.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const tourImages = {
    // Local images in public/images/ folder (copy your .jpg files there)
    // Fallback to Unsplash URLs if local images not found
    1: "https://images.unsplash.com/photo-1488646953014-85cb44e25828?w=500&h=300&fit=crop", // Hà Nội-Hạ Long
    2: "https://images.unsplash.com/photo-1511784642898-4c92249e20b6?w=500&h=300&fit=crop", // Sài Gòn-Cần Thơ
    3: "https://images.unsplash.com/photo-1559827260-dc66d52bef19?w=500&h=300&fit=crop", // Đà Nẵng-Hội An
    4: "https://images.unsplash.com/photo-1552733407-5d5c46b3da3b?w=500&h=300&fit=crop", // Phú Quốc
    5: "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=500&h=300&fit=crop", // Sapa-Fansipan
  };

  if (loading) {
    return (
      <div className="tour-list-container">
        <div
          style={{ textAlign: "center", color: "white", fontSize: "1.2rem" }}
        >
          Đang tải danh sách tour...
        </div>
      </div>
    );
  }

  return (
    <div className="tour-list-container">
      <div className="tour-list-header">
        <h2>Khám Phá Các Tour Du Lịch</h2>
        <p>Chọn một tour yêu thích và bắt đầu hành trình của bạn</p>
      </div>

      {error && (
        <div
          style={{ color: "white", textAlign: "center", marginBottom: "1rem" }}
        >
          {error}
        </div>
      )}

      <div className="tours-grid">
        {tours.map((tour) => (
          <div key={tour.id} className="tour-card">
            <img
              src={tourImages[tour.id]}
              alt={tour.name}
              className="tour-image"
            />
            <div className="tour-content">
              <h3 className="tour-name">{tour.name}</h3>
              <p className="tour-info">✈️ {tour.duration} ngày</p>
              <p className="tour-info">📍 {tour.location}</p>
              <p className="tour-description">{tour.description}</p>
              <div className="tour-highlights">
                {tour.highlights &&
                  tour.highlights.slice(0, 2).map((highlight, idx) => (
                    <span key={idx} className="highlight">
                      {highlight}
                    </span>
                  ))}
              </div>
              <p className="tour-price">
                {tour.price.toLocaleString("vi-VN")} VND
              </p>
              <button className="select-btn" onClick={() => onSelectTour(tour)}>
                Chọn Tour
              </button>
            </div>
          </div>
        ))}
      </div>

    </div>
  );
};

export default TourList;
