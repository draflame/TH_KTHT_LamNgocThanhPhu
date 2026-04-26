package iuh.fit.booking_service.controller;


import iuh.fit.booking_service.dto.BookingRequest;
import iuh.fit.booking_service.model.Booking;
import iuh.fit.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest request) {
        Booking createdBooking = bookingService.createBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
    }
}
