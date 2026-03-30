package iuh.fit.payment.strategy;

public class PaymentStrategyContext {
    private PaymentStrategy strategy;

    public PaymentStrategyContext(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean pay(Payment payment) {
        if (strategy == null) {
            throw new IllegalStateException("Payment strategy not set");
        }

        return strategy.processPayment(payment);
    }

    public double calculateTotalAmount(Payment payment) {
        double fee = strategy.getProcessingFee(payment.getAmount());
        return payment.getAmount() + fee;
    }

    public String getStrategyName() {
        return strategy == null ? "N/A" : strategy.getMethodName();
    }
}
