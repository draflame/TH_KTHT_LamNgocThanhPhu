package iuh.fit.strategyPattern;

public interface OrderOperationStrategy {
    void execute(StrategyOrder order);

    String getStrategyName();
}
