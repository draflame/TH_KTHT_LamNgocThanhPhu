package iuh.fit.payment.strategy;

public class BankTransferPaymentStrategy implements PaymentStrategy {
    private static final double PROCESSING_FEE_RATE = 0.015; // 1.5%

    @Override
    public boolean processPayment(Payment payment) {
        System.out.println("[BANK TRANSFER] Initiating bank transfer for " + payment.getCustomer());
        System.out.println("[BANK TRANSFER] Amount: " + payment.getAmount());

        // Simulate bank transfer validation
        if (payment.getAmount() > 0 && payment.getAmount() <= 50000000) {
            System.out.println("[BANK TRANSFER] Bank transfer queued!");
            return true;
        }

        System.out.println("[BANK TRANSFER] Payment failed - exceeded limit");
        return false;
    }

    @Override
    public String getMethodName() {
        return "Bank Transfer (1.5% fee)";
    }

    @Override
    public double getProcessingFee(double amount) {
        return amount * PROCESSING_FEE_RATE;
    }
}
