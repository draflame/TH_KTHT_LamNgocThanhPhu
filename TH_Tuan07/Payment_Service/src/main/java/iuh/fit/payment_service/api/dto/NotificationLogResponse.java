package iuh.fit.payment_service.api.dto;

import java.time.LocalDateTime;

public record NotificationLogResponse(
        Long id,
        Long bookingId,
        Long userId,
        String message,
        LocalDateTime sentAt
) {
}

