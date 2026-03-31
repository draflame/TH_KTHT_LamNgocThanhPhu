package iuh.fit.payment.decorator;

public class BasePaymentComponent implements PaymentComponent {
    private final String method;
    private final String customer;
    private final double amount;

    public BasePaymentComponent(String method, String customer, double amount) {
        this.method = method;
        this.customer = customer;
        this.amount = amount;
    }

    @Override
    public double getFinalAmount() {
        return amount;
    }

    @Override
    public String getDescription() {
        return method + " payment for " + customer;
    }

    @Override
    public boolean executePayment() {
        System.out.println("[BASE] Processing " + method + " payment for " + customer);
        System.out.printf("[BASE] Amount: %.0f VND%n", amount);
        return amount > 0;
    }
}
