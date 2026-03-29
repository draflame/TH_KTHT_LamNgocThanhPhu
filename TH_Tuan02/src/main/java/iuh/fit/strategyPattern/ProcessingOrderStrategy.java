package iuh.fit.strategyPattern;

public class ProcessingOrderStrategy implements OrderOperationStrategy {
    @Override
    public void execute(StrategyOrder order) {
        if (order.isCancelled()) {
            System.out.println("[DANG XU LY] Don hang da huy, khong the dong goi/van chuyen.");
            return;
        }

        System.out.println("[DANG XU LY] Dong goi va van chuyen don hang " + order.getOrderId());
    }

    @Override
    public String getStrategyName() {
        return "ProcessingOrderStrategy";
    }
}
