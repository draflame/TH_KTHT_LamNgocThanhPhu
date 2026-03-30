package iuh.fit.payment.strategy;

public class CreditCardPaymentStrategy implements PaymentStrategy {
    private static final double PROCESSING_FEE_RATE = 0.025; // 2.5%

    @Override
    public boolean processPayment(Payment payment) {
        System.out.println("[CREDIT CARD] Processing payment for " + payment.getCustomer());
        System.out.println("[CREDIT CARD] Amount: " + payment.getAmount());
        
        // Simulate payment validation
        if (payment.getAmount() > 0) {
            System.out.println("[CREDIT CARD] Payment authorized!");
            return true;
        }
        
        System.out.println("[CREDIT CARD] Payment failed - invalid amount");
        return false;
    }

    @Override
    public String getMethodName() {
        return "Credit Card (2.5% fee)";
    }

    @Override
    public double getProcessingFee(double amount) {
        return amount * PROCESSING_FEE_RATE;
    }
}
