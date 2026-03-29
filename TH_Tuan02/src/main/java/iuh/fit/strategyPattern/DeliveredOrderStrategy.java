package iuh.fit.strategyPattern;

public class DeliveredOrderStrategy implements OrderOperationStrategy {
    @Override
    public void execute(StrategyOrder order) {
        if (order.isCancelled()) {
            System.out.println("[DA GIAO] Don hang da huy, khong the cap nhat da giao.");
            return;
        }

        order.markDelivered();
        System.out.println("[DA GIAO] Cap nhat don hang " + order.getOrderId() + " la da giao.");
    }

    @Override
    public String getStrategyName() {
        return "DeliveredOrderStrategy";
    }
}
