package iuh.fit.statePattern;

public interface OrderState {
    void handle(Order order);

    String getStateName();
}
