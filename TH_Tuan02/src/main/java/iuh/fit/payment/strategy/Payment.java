package iuh.fit.payment.strategy;

public class Payment {
    private final String transactionId;
    private final String customer;
    private final double amount;
    private final PaymentMethod method;

    public Payment(String transactionId, String customer, double amount, PaymentMethod method) {
        this.transactionId = transactionId;
        this.customer = customer;
        this.amount = amount;
        this.method = method;
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

    public PaymentMethod getMethod() {
        return method;
    }
}
