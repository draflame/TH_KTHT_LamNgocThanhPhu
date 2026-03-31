package iuh.fit.payment.state;

public class CompletedPaymentState implements PaymentState {
    @Override
    public void process(PaymentTransaction transaction) {
        System.out.println("[COMPLETED] Cannot process - payment already completed");
    }

    @Override
    public void complete(PaymentTransaction transaction) {
        System.out.println("[COMPLETED] Payment already completed");
    }

    @Override
    public void fail(PaymentTransaction transaction) {
        System.out.println("[COMPLETED] Cannot fail - payment already completed, initiating refund request");
    }

    @Override
    public String getStateName() {
        return "COMPLETED";
    }
}
