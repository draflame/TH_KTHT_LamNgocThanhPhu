package iuh.fit.strategyPattern;

public class StrategyOrder {
    private final String orderId;
    private final String customerName;
    private final double amount;
    private boolean delivered;
    private boolean cancelled;

    public StrategyOrder(String orderId, String customerName, double amount) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.amount = amount;
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

    public boolean isDelivered() {
        return delivered;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void markDelivered() {
        this.delivered = true;
    }

    public void markCancelled() {
        this.cancelled = true;
    }

    public void displayInfo() {
        System.out.println("--- THONG TIN DON HANG (STRATEGY) ---");
        System.out.println("ID: " + orderId);
        System.out.println("Khach hang: " + customerName);
        System.out.println("So tien: " + amount);
        System.out.println("Da giao: " + (delivered ? "Co" : "Chua"));
        System.out.println("Da huy: " + (cancelled ? "Co" : "Chua"));
        System.out.println("-------------------------------------\n");
    }
}
