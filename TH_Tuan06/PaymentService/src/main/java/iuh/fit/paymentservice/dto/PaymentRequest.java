package iuh.fit.paymentservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private Integer orderId;
    private String paymentMethod;      // "COD" hoặc "BANKING"
    private BigDecimal amount;
    private String transactionId;      // Null nếu là COD, có giá trị nếu BANKING
    private String userName;           // Dùng cho notification log
}
