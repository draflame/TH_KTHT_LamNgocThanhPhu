package iuh.fit.payment.decorator;

public interface PaymentComponent {
    double getFinalAmount();

    String getDescription();

    boolean executePayment();
}
