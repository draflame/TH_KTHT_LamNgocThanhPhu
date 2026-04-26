package iuh.fit.payment_service.messaging;

import iuh.fit.payment_service.messaging.event.PaymentResultEvent;
import iuh.fit.payment_service.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentResultConsumer {

    private final NotificationService notificationService;

    public PaymentResultConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "${app.kafka.topics.payment-completed:payment.completed}", groupId = "${spring.kafka.consumer.group-id:payment-service-group}")
    public void onPaymentCompleted(PaymentResultEvent event) {
        notificationService.handlePaymentResult(event);
    }

    @KafkaListener(topics = "${app.kafka.topics.booking-failed:booking.failed}", groupId = "${spring.kafka.consumer.group-id:payment-service-group}")
    public void onBookingFailed(PaymentResultEvent event) {
        notificationService.handlePaymentResult(event);
    }
}

