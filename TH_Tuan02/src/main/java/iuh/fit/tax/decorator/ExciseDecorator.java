package iuh.fit.tax.decorator;

public class ExciseDecorator extends TaxDecorator {
    private static final double EXCISE_RATE = 0.15;

    public ExciseDecorator(TaxComponent wrapped) {
        super(wrapped);
    }

    @Override
    public double totalPrice() {
        double current = wrapped.totalPrice();
        return current + current * EXCISE_RATE;
    }

    @Override
    public String breakdown() {
        return wrapped.breakdown() + " + Excise 15%";
    }
}
