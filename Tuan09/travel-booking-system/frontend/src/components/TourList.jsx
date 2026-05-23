import React, { useState, useEffect } from "react";
import { orchestratorAPI } from "../services/api";
import "../styles/TourList.css";

const TourList = ({ user, onSelectTour }) => {
  const [tours, setTours] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    loadTours();
  }, []);

  const loadTours = async () => {
    try {
      setLoading(true);
      const response = await orchestratorAPI.getTours();
      setTours(response.tours || response);
    } catch (err) {
      setError(err.message || "Lỗi tải danh sách tour");
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <div className="loading">Đang tải danh sách tour...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="tour-list-container">
      <h2>📍 Danh sách tour</h2>
      <div className="tours-grid">
        {tours.map((tour) => (
          <div key={tour.id} className="tour-card">
            <h3>{tour.name}</h3>
            <p className="duration">⏱️ Thời gian: {tour.duration} ngày</p>
            <p className="description">{tour.description}</p>
            <p className="price">💰 Giá: {tour.price.toLocaleString()} VND</p>
            <button onClick={() => onSelectTour(tour)} className="book-btn">
              Chọn tour này
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default TourList;
