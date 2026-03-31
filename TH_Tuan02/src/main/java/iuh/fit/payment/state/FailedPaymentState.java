package iuh.fit.payment.state;

public class FailedPaymentState implements PaymentState {
    @Override
    public void process(PaymentTransaction transaction) {
        System.out.println("[FAILED] Cannot process - payment already failed");
    }

    @Override
    public void complete(PaymentTransaction transaction) {
        System.out.println("[FAILED] Cannot complete - payment has failed");
    }

    @Override
    public void fail(PaymentTransaction transaction) {
        System.out.println("[FAILED] Payment remains failed - can retry");
        transaction.setState(new PendingPaymentState());
    }

    @Override
    public String getStateName() {
        return "FAILED";
    }
}
