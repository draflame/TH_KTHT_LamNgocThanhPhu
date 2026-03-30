package iuh.fit.strategyPattern;

public class CancelOrderStrategy implements OrderOperationStrategy {
    @Override
    public void execute(StrategyOrder order) {
        if (order.isDelivered()) {
            System.out.println("[HUY] Don hang da giao, khong the huy.");
            return;

        }

        order.markCancelled();
        System.out.println("[HUY] Huy don hang " + order.getOrderId() + " va hoan tien " + order.getAmount() + " VND.");
    }

    @Override
    public String getStrategyName() {
        return "CancelOrderStrategy";
    }
}
