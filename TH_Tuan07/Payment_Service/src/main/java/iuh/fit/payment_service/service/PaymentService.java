package iuh.fit.payment_service.service;

import iuh.fit.payment_service.api.dto.PaymentResponse;
import iuh.fit.payment_service.domain.PaymentStatus;
import iuh.fit.payment_service.messaging.PaymentEventPublisher;
import iuh.fit.payment_service.messaging.event.BookingCreatedEvent;
import iuh.fit.payment_service.messaging.event.PaymentResultEvent;
import iuh.fit.payment_service.persistence.entity.PaymentEntity;
import iuh.fit.payment_service.persistence.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher paymentEventPublisher;

    public PaymentService(PaymentRepository paymentRepository, PaymentEventPublisher paymentEventPublisher) {
        this.paymentRepository = paymentRepository;
        this.paymentEventPublisher = paymentEventPublisher;
    }

    @Transactional
    public void processBooking(BookingCreatedEvent bookingEvent) {
        if (bookingEvent.bookingId() == null || bookingEvent.userId() == null || bookingEvent.amount() == null) {
            return;
        }

        if (paymentRepository.existsByBookingId(bookingEvent.bookingId())) {
            return;
        }

        boolean success = ThreadLocalRandom.current().nextBoolean();
        PaymentStatus paymentStatus = success ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;

        PaymentEntity payment = new PaymentEntity();
        payment.setBookingId(bookingEvent.bookingId());
        payment.setAmount(bookingEvent.amount());
        payment.setStatus(paymentStatus);
        payment.setTransactionId("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        PaymentEntity savedPayment = paymentRepository.save(payment);

        String eventType = success ? "PAYMENT_COMPLETED" : "BOOKING_FAILED";
        String message = success
                ? "Booking #" + savedPayment.getBookingId() + " thanh cong"
                : "Booking #" + savedPayment.getBookingId() + " that bai";

        PaymentResultEvent resultEvent = new PaymentResultEvent(
                UUID.randomUUID().toString(),
                eventType,
                savedPayment.getBookingId(),
                bookingEvent.userId(),
                savedPayment.getAmount(),
                savedPayment.getStatus().name(),
                savedPayment.getTransactionId(),
                message,
                Instant.now()
        );

        paymentEventPublisher.publish(resultEvent);
    }

    @Transactional(readOnly = true)
    public Optional<PaymentResponse> getPaymentByBookingId(Long bookingId) {
        return paymentRepository.findByBookingId(bookingId)
                .map(payment -> new PaymentResponse(
                        payment.getId(),
                        payment.getBookingId(),
                        payment.getAmount(),
                        payment.getStatus().name(),
                        payment.getTransactionId(),
                        payment.getCreatedAt()
                ));
    }
}

