package iuh.fit.payment.decorator;

public abstract class PaymentDecorator implements PaymentComponent {
    protected final PaymentComponent wrapped;

    protected PaymentDecorator(PaymentComponent wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public boolean executePayment() {
        return wrapped.executePayment();
    }
}
