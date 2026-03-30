package iuh.fit.tax.decorator;

public class VatDecorator extends TaxDecorator {
    private static final double VAT_RATE = 0.10;

    public VatDecorator(TaxComponent wrapped) {
        super(wrapped);
    }

    @Override
    public double totalPrice() {
        double current = wrapped.totalPrice();
        return current + current * VAT_RATE;
    }

    @Override
    public String breakdown() {
        return wrapped.breakdown() + " + VAT 10%";
    }
}
