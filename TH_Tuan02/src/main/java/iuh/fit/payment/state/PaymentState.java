package iuh.fit.payment.state;

public interface PaymentState {
    void process(PaymentTransaction transaction);

    void complete(PaymentTransaction transaction);

    void fail(PaymentTransaction transaction);

    String getStateName();
}
