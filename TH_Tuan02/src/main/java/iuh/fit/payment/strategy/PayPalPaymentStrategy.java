package iuh.fit.payment.strategy;

public class PayPalPaymentStrategy implements PaymentStrategy {
    private static final double PROCESSING_FEE_RATE = 0.035; // 3.5%

    @Override
    public boolean processPayment(Payment payment) {
        System.out.println("[PAYPAL] Redirecting to PayPal for " + payment.getCustomer());
        System.out.println("[PAYPAL] Amount: " + payment.getAmount());

        // Simulate PayPal validation
        if (payment.getAmount() > 0 && payment.getCustomer() != null) {
            System.out.println("[PAYPAL] Transaction authorized via PayPal!");
            return true;
        }

        System.out.println("[PAYPAL] Payment failed");
        return false;
    }

    @Override
    public String getMethodName() {
        return "PayPal (3.5% fee)";
    }

    @Override
    public double getProcessingFee(double amount) {
        return amount * PROCESSING_FEE_RATE;
    }
}
