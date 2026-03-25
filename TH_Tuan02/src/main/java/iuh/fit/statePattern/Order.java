package iuh.fit.statePattern;

public class Order {
    private String orderId;
    private String customerName;
    private double amount;
    private OrderState currentState;

    public Order(String orderId, String customerName, double amount) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.amount = amount;
        this.currentState = new NewOrder();
    }

    public void setState(OrderState state) {
        this.currentState = state;
    }

    public void processOrder() {
        currentState.handle(this);
    }

    public void cancelOrder() {
        System.out.println(">>> Yeu cau huy don hang <<<\n");
        currentState = new CancelledOrder();
        currentState.handle(this);
    }

    public OrderState getState() {
        return currentState;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getAmount() {
        return amount;
    }

    public void displayInfo() {
        System.out.println("--- THONG TIN DON HANG ---");
        System.out.println("ID: " + orderId);
        System.out.println("Khach hang: " + customerName);
        System.out.println("So tien: " + amount);
        System.out.println("Trang thai hien tai: " + currentState.getStateName());
        System.out.println("-------------------------\n");
    }
}
