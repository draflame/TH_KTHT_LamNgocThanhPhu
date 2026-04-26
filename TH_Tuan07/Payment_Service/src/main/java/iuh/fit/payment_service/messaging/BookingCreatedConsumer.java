package iuh.fit.payment_service.messaging;

import iuh.fit.payment_service.messaging.event.BookingCreatedEvent;
import iuh.fit.payment_service.service.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BookingCreatedConsumer {

    private final PaymentService paymentService;

    public BookingCreatedConsumer(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "${app.kafka.topics.booking-created:booking.created}", groupId = "${spring.kafka.consumer.group-id:payment-service-group}")
    public void onBookingCreated(BookingCreatedEvent event) {
        paymentService.processBooking(event);
    }
}

