package iuh.fit.payment_service.messaging.event;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentResultEvent(
        String eventId,
        String eventType,
        Long bookingId,
        Long userId,
        BigDecimal amount,
        String status,
        String transactionId,
        String message,
        Instant createdAt
) {
}

