package iuh.fit.payment.decorator;

public class DiscountCodeDecorator extends PaymentDecorator {
    private final String code;
    private final double discountRate;

    public DiscountCodeDecorator(PaymentComponent wrapped, String code, double discountRate) {
        super(wrapped);
        this.code = code;
        this.discountRate = discountRate;
    }

    @Override
    public double getFinalAmount() {
        double amount = wrapped.getFinalAmount();
        return amount - (amount * discountRate);
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " + Discount Code " + code + " (" + (discountRate * 100) + "%)";
    }

    @Override
    public boolean executePayment() {
        boolean success = super.executePayment();
        if (success) {
            System.out.printf("[DISCOUNT] Applied code %s: -%.0f VND%n", code, wrapped.getFinalAmount() * discountRate);
        }
        return success;
    }
}
