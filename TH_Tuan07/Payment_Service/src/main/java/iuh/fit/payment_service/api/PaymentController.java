package iuh.fit.payment_service.api;

import iuh.fit.payment_service.api.dto.PaymentResponse;
import iuh.fit.payment_service.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<PaymentResponse> getByBookingId(@PathVariable Long bookingId) {
        return paymentService.getPaymentByBookingId(bookingId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

