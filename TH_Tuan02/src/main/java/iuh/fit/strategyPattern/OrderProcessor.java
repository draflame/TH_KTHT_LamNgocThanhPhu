package iuh.fit.strategyPattern;

public class OrderProcessor {
    private OrderOperationStrategy strategy;

    public OrderProcessor(OrderOperationStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(OrderOperationStrategy strategy) {
        this.strategy = strategy;
    }

    public void process(StrategyOrder order) {
        if (strategy == null) {
            System.out.println("Chua co chien luoc xu ly don hang.");
            return;
        }

        System.out.println("Dang dung chien luoc: " + strategy.getStrategyName());
        strategy.execute(order);
    }
}
