package iuh.fit.paymentservice.service;

import iuh.fit.paymentservice.dto.PaymentRequest;
import iuh.fit.paymentservice.model.Payment;
import iuh.fit.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final NotificationService notificationService;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${order.service.url}")
    private String orderServiceUrl;

    public Payment processPayment(PaymentRequest req) {

        // 1. Validate method
        Payment.PaymentMethod method;
        try {
            method = Payment.PaymentMethod.valueOf(req.getPaymentMethod().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Phương thức thanh toán không hợp lệ: " + req.getPaymentMethod());
        }

        // 2. Tạo và lưu payment
        Payment payment = new Payment();
        payment.setOrderId(req.getOrderId());
        payment.setPaymentMethod(method);
        payment.setAmount(req.getAmount());
        payment.setTransactionId(req.getTransactionId()); // null nếu COD
        payment.setStatus(Payment.PaymentStatus.SUCCESS);  // giả lập luôn thành công

        payment = paymentRepository.save(payment);

        // 3. Gọi Order Service cập nhật trạng thái
        try {
            String url = orderServiceUrl + "/orders/" + req.getOrderId() + "/status";
            Map<String, String> body = Map.of("status", "PAID");
            restTemplate.put(url, body);
            log.info("✅ Đã cập nhật trạng thái đơn #{} sang PAID", req.getOrderId());
        } catch (Exception e) {
            log.warn("⚠️ Không thể cập nhật Order Service: {}", e.getMessage());
        }

        // 4. Gửi notification
        notificationService.sendNotification(payment.getId(), req.getOrderId(), req.getUserName());

        return payment;
    }
}