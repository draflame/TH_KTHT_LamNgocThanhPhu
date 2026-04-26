package iuh.fit.payment_service.api;

import iuh.fit.payment_service.api.dto.NotificationLogResponse;
import iuh.fit.payment_service.service.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/booking/{bookingId}")
    public List<NotificationLogResponse> getByBookingId(@PathVariable Long bookingId) {
        return notificationService.getNotificationsByBookingId(bookingId);
    }
}

