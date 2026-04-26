package iuh.fit.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingCreatedEvent {
    private Integer bookingId;
    private Integer userId;
    private Integer movieId;
    private String seatNumber;
}
