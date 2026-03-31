package iuh.fit.payment.state;

public class PaymentTransaction {
    private final String transactionId;
    private final String customer;
    private final double amount;
    private PaymentState state;

    public PaymentTransaction(String transactionId, String customer, double amount) {
        this.transactionId = transactionId;
        this.customer = customer;
        this.amount = amount;
        this.state = new PendingPaymentState();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getCustomer() {
        return customer;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentState getState() {
        return state;
    }

    public void setState(PaymentState state) {
        this.state = state;
    }

    public void process() {
        state.process(this);
    }

    public void complete() {
        state.complete(this);
    }

    public void fail() {
        state.fail(this);
    }
}
