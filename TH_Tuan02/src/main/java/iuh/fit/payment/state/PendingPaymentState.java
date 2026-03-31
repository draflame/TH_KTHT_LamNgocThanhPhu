package iuh.fit.payment.state;

public class PendingPaymentState implements PaymentState {
    @Override
    public void process(PaymentTransaction transaction) {
        System.out.println("[PENDING] Starting payment processing for transaction " + transaction.getTransactionId());
        transaction.setState(new ProcessingPaymentState());
    }

    @Override
    public void complete(PaymentTransaction transaction) {
        System.out.println("[PENDING] Cannot complete payment - still pending");
    }

    @Override
    public void fail(PaymentTransaction transaction) {
        System.out.println("[PENDING] Transaction cancelled");
        transaction.setState(new FailedPaymentState());
    }

    @Override
    public String getStateName() {
        return "PENDING";
    }
}
