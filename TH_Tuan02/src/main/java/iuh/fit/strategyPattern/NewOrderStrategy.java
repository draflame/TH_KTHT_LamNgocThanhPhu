package iuh.fit.strategyPattern;

public class NewOrderStrategy implements OrderOperationStrategy {
    @Override
    public void execute(StrategyOrder order) {
        System.out.println("[MOI TAO] Kiem tra thong tin don hang " + order.getOrderId());
    }

    @Override
    public String getStrategyName() {
        return "NewOrderStrategy";
    }
}
