package iuh.fit.payment_service.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
        Long id,
        Long bookingId,
        BigDecimal amount,
        String status,
        String transactionId,
        LocalDateTime createdAt
) {
}

