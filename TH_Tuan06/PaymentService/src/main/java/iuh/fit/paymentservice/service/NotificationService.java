package iuh.fit.paymentservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    public void sendNotification(Integer paymentId, Integer orderId, String userName) {
        log.info("🔔 {} đã đặt đơn #{} thành công! Mã thanh toán: #{}", userName, orderId, paymentId);
    }
}
