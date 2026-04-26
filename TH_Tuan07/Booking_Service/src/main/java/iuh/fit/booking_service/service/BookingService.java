package iuh.fit.booking_service.service;

import iuh.fit.booking_service.dto.BookingCreatedEvent;
import iuh.fit.booking_service.dto.BookingRequest;
import iuh.fit.booking_service.model.Booking;
import iuh.fit.booking_service.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String BOOKING_TOPIC = "BOOKING_CREATED";

    @Transactional
    public Booking createBooking(BookingRequest request) {
        // 1. Lưu thông tin Booking (Status mặc định là PENDING nhờ Entity)
        Booking booking = Booking.builder()
                .userId(request.getUserId())
                .movieId(request.getMovieId())
                .seatNumber(request.getSeatNumber())
                .build();

        booking = bookingRepository.save(booking);
        log.info("Saved booking {} to database with status PENDING", booking.getId());

        // 2. Publish event ra Message Broker
        BookingCreatedEvent event = new BookingCreatedEvent(
                booking.getId(),
                booking.getUserId(),
                booking.getMovieId(),
                booking.getSeatNumber()
        );

        kafkaTemplate.send(BOOKING_TOPIC, event);
        log.info("Published {} event for booking {}", BOOKING_TOPIC, booking.getId());

        // 3. Trả về kết quả ngay lập tức
        return booking;
    }
}
