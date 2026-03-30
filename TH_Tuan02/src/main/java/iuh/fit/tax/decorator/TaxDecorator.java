package iuh.fit.tax.decorator;

public abstract class TaxDecorator implements TaxComponent {
    protected final TaxComponent wrapped;

    protected TaxDecorator(TaxComponent wrapped) {
        this.wrapped = wrapped;
    }
}
