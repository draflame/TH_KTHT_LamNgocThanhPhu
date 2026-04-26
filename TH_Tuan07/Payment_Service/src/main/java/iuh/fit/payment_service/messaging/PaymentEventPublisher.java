package iuh.fit.payment_service.messaging;

import iuh.fit.payment_service.messaging.event.PaymentResultEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventPublisher {

    private final KafkaTemplate<String, PaymentResultEvent> kafkaTemplate;
    private final String paymentCompletedTopic;
    private final String bookingFailedTopic;

    public PaymentEventPublisher(
            KafkaTemplate<String, PaymentResultEvent> kafkaTemplate,
            @Value("${app.kafka.topics.payment-completed:payment.completed}") String paymentCompletedTopic,
            @Value("${app.kafka.topics.booking-failed:booking.failed}") String bookingFailedTopic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.paymentCompletedTopic = paymentCompletedTopic;
        this.bookingFailedTopic = bookingFailedTopic;
    }

    public void publish(PaymentResultEvent event) {
        String topic = "SUCCESS".equalsIgnoreCase(event.status()) ? paymentCompletedTopic : bookingFailedTopic;
        kafkaTemplate.send(topic, String.valueOf(event.bookingId()), event);
    }
}

