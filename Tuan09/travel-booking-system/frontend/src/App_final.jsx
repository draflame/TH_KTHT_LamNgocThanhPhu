import React, { useState, useEffect } from "react";
import Login from "./components/Login";
import TourList_new from "./components/TourList_new";
import BookingForm_new from "./components/BookingForm_new";
import BookingSuccess from "./components/BookingSuccess";
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
      {currentPage === "login" && <Login onLoginSuccess={handleLoginSuccess} />}

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
        <BookingSuccess
          bookingResult={bookingResult}
          onNewBooking={handleNewBooking}
          onLogout={handleLogout}
        />
      )}
    </div>
  );
}

export default App;
