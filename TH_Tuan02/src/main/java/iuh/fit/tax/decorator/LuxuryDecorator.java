package iuh.fit.tax.decorator;

public class LuxuryDecorator extends TaxDecorator {
    private static final double LUXURY_RATE = 0.20;

    public LuxuryDecorator(TaxComponent wrapped) {
        super(wrapped);
    }

    @Override
    public double totalPrice() {
        double current = wrapped.totalPrice();
        return current + current * LUXURY_RATE;
    }

    @Override
    public String breakdown() {
        return wrapped.breakdown() + " + Luxury 20%";
    }
}
