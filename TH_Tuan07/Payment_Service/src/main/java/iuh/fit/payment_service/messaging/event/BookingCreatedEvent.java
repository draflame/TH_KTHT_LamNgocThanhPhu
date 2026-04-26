package iuh.fit.payment_service.messaging.event;

import java.math.BigDecimal;
import java.time.Instant;

public record BookingCreatedEvent(
        String eventId,
        Long bookingId,
        Long userId,
        BigDecimal amount,
        Instant createdAt
) {
}

