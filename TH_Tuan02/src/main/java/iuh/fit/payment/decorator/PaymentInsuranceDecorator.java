package iuh.fit.payment.decorator;

public class PaymentInsuranceDecorator extends PaymentDecorator {
    private static final double INSURANCE_FEE = 50000;

    public PaymentInsuranceDecorator(PaymentComponent wrapped) {
        super(wrapped);
    }

    @Override
    public double getFinalAmount() {
        return wrapped.getFinalAmount() + INSURANCE_FEE;
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " + Payment Insurance (50,000 VND)";
    }

    @Override
    public boolean executePayment() {
        boolean success = super.executePayment();
        if (success) {
            System.out.printf("[INSURANCE] Added insurance fee: +%.0f VND%n", INSURANCE_FEE);
        }
        return success;
    }
}
