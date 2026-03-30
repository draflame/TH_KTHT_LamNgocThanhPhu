package iuh.fit.payment.strategy;

public interface PaymentStrategy {
    boolean processPayment(Payment payment);

    String getMethodName();

    double getProcessingFee(double amount);
}
