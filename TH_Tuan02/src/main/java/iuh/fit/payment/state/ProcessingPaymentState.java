package iuh.fit.payment.state;

public class ProcessingPaymentState implements PaymentState {
    @Override
    public void process(PaymentTransaction transaction) {
        System.out.println("[PROCESSING] Payment is already being processed");
    }

    @Override
    public void complete(PaymentTransaction transaction) {
        System.out.println("[PROCESSING] Successfully processed payment of " + transaction.getAmount() + " VND");
        transaction.setState(new CompletedPaymentState());
    }

    @Override
    public void fail(PaymentTransaction transaction) {
        System.out.println("[PROCESSING] Payment processing failed - reverting funds");
        transaction.setState(new FailedPaymentState());
    }

    @Override
    public String getStateName() {
        return "PROCESSING";
    }
}
