package iuh.fit.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class BookingRequest {
    private Integer userId;
    private Integer movieId;
    private String seatNumber;
}

