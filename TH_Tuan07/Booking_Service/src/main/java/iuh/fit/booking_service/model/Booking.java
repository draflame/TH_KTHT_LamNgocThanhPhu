package iuh.fit.booking_service.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "movie_id", nullable = false)
    private Integer movieId;

    @Column(name = "seat_number", nullable = false, length = 10)
    private String seatNumber;

    @CreationTimestamp
    @Column(name = "booking_date", updatable = false)
    private LocalDateTime bookingDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @Builder.Default
    private BookingStatus status = BookingStatus.PENDING;

    public enum BookingStatus {
        PENDING, SUCCESS, FAILED
    }
}