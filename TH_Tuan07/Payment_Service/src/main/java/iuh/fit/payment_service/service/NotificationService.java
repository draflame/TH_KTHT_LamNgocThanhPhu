package iuh.fit.payment_service.service;

import iuh.fit.payment_service.api.dto.NotificationLogResponse;
import iuh.fit.payment_service.messaging.event.PaymentResultEvent;
import iuh.fit.payment_service.persistence.entity.NotificationLogEntity;
import iuh.fit.payment_service.persistence.repository.NotificationLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationLogRepository notificationLogRepository;

    public NotificationService(NotificationLogRepository notificationLogRepository) {
        this.notificationLogRepository = notificationLogRepository;
    }

    @Transactional
    public void handlePaymentResult(PaymentResultEvent event) {
        if (event.bookingId() == null || event.userId() == null) {
            return;
        }

        String message = "SUCCESS".equalsIgnoreCase(event.status())
                ? "User " + event.userId() + " da dat don #" + event.bookingId() + " thanh cong"
                : "User " + event.userId() + " thanh toan that bai cho don #" + event.bookingId();

        NotificationLogEntity logEntity = new NotificationLogEntity();
        logEntity.setBookingId(event.bookingId());
        logEntity.setUserId(event.userId());
        logEntity.setMessage(message);
        notificationLogRepository.save(logEntity);

        LOGGER.info(message);
    }

    @Transactional(readOnly = true)
    public List<NotificationLogResponse> getNotificationsByBookingId(Long bookingId) {
        return notificationLogRepository.findByBookingId(bookingId).stream()
                .map(log -> new NotificationLogResponse(
                        log.getId(),
                        log.getBookingId(),
                        log.getUserId(),
                        log.getMessage(),
                        log.getSentAt()
                ))
                .toList();
    }
}

