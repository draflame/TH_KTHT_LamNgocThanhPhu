package iuh.fit.paymentservice.controller;

import iuh.fit.paymentservice.dto.PaymentRequest;
import iuh.fit.paymentservice.model.Payment;
import iuh.fit.paymentservice.repository.PaymentRepository;
import iuh.fit.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    // Tạo thanh toán mới
    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody PaymentRequest req) {
        Payment payment = paymentService.processPayment(req);
        return ResponseEntity.ok(payment);
    }

    // Lấy lịch sử thanh toán theo orderId
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<Payment>> getByOrderId(@PathVariable Integer orderId) {
        return ResponseEntity.ok(paymentRepository.findByOrderId(orderId));
    }

    // Lấy chi tiết 1 payment
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getById(@PathVariable Integer id) {
        return paymentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}