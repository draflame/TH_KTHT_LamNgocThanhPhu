import React, { useState, useEffect } from "react";
import Login_new from "./components/Login_new";
import TourList_new from "./components/TourList_new";
import BookingForm_new from "./components/BookingForm_new";
import BookingSuccess_new from "./components/BookingSuccess_new";
import "./App.css";

function App() {
  const [currentPage, setCurrentPage] = useState("login");
  const [user, setUser] = useState(null);
  const [selectedTour, setSelectedTour] = useState(null);
  const [bookingResult, setBookingResult] = useState(null);

  useEffect(() => {
    const savedUser = localStorage.getItem("user");
    if (savedUser) {
      setUser(JSON.parse(savedUser));
      setCurrentPage("tours");
    }
  }, []);

  const handleLoginSuccess = (userData) => {
    setUser(userData);
    setCurrentPage("tours");
  };

  const handleSelectTour = (tour) => {
    setSelectedTour(tour);
    setCurrentPage("booking");
  };

  const handleBookingComplete = (result) => {
    setBookingResult(result);
    setCurrentPage("success");
  };

  const handleLogout = () => {
    localStorage.removeItem("user");
    setUser(null);
    setSelectedTour(null);
    setBookingResult(null);
    setCurrentPage("login");
  };

  const handleNewBooking = () => {
    setSelectedTour(null);
    setBookingResult(null);
    setCurrentPage("tours");
  };

  return (
    <div className="App">
      {user && currentPage !== "login" && (
        <nav className="navbar">
          <div className="navbar-logo" onClick={() => setCurrentPage("tours")}>
            🌍 <span>DraTravel</span>
          </div>
          <div className="navbar-links">
            <button
              className={`nav-link ${currentPage === "tours" ? "active" : ""}`}
              onClick={() => setCurrentPage("tours")}
            >
              🏕️ Danh sách Tour
            </button>
            <button
              className={`nav-link ${currentPage === "booking" ? "active" : ""}`}
            >
              🎫 Đặt Tour
            </button>
          </div>
          <div className="navbar-user">
            <div className="user-profile">
              <span className="avatar">👤</span>
              <span className="user-name">{user?.fullName || user?.username}</span>
            </div>
            <button className="nav-logout-btn" onClick={handleLogout}>
              Đăng Xuất
            </button>
          </div>
        </nav>
      )}

      <main className="main-content">
        {currentPage === "login" && (
          <Login_new onLoginSuccess={handleLoginSuccess} />
        )}

        {currentPage === "tours" && user && (
          <TourList_new onSelectTour={handleSelectTour} onLogout={handleLogout} />
        )}

        {currentPage === "booking" && selectedTour && user && (
          <BookingForm_new
            tour={selectedTour}
            user={user}
            onBookingComplete={handleBookingComplete}
            onBack={() => setCurrentPage("tours")}
          />
        )}

        {currentPage === "success" && bookingResult && (
          <BookingSuccess_new
            bookingResult={bookingResult}
            onNewBooking={handleNewBooking}
            onLogout={handleLogout}
          />
        )}
      </main>
    </div>
  );
}

export default App;
