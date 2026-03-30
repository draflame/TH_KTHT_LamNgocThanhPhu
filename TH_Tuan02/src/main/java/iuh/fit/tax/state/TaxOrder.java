package iuh.fit.tax.state;

public class TaxOrder {
    private final String orderId;
    private final TaxableProduct product;
    private OrderTaxState state;
    private double lastTax;

    public TaxOrder(String orderId, TaxableProduct product) {
        this.orderId = orderId;
        this.product = product;
        this.state = new NewOrderTaxState();
    }

    public String getOrderId() {
        return orderId;
    }

    public TaxableProduct getProduct() {
        return product;
    }

    public double getLastTax() {
        return lastTax;
    }

    public void setLastTax(double lastTax) {
        this.lastTax = lastTax;
    }

    public OrderTaxState getState() {
        return state;
    }

    public void setState(OrderTaxState state) {
        this.state = state;
    }

    public double calculateTax() {
        double tax = state.calculateTax(this);
        this.lastTax = tax;
        return tax;
    }

    public void moveToNextState() {
        state.next(this);
    }
}
