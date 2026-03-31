package iuh.fit.payment.decorator;

public class ProcessingFeeDecorator extends PaymentDecorator {
    private static final double FEE_RATE = 0.03; // 3%

    public ProcessingFeeDecorator(PaymentComponent wrapped) {
        super(wrapped);
    }

    @Override
    public double getFinalAmount() {
        double amount = wrapped.getFinalAmount();
        return amount + (amount * FEE_RATE);
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " + Processing Fee (3%)";
    }

    @Override
    public boolean executePayment() {
        boolean success = super.executePayment();
        if (success) {
            System.out.printf("[FEE] Added processing fee (3%%): +%.0f VND%n", wrapped.getFinalAmount() * FEE_RATE);
        }
        return success;
    }
}
