package iuh.fit.statePattern;

public class ShippedOrder implements OrderState {
    @Override
    public void handle(Order order) {
        System.out.println("[DA GIAO] Don hang " + order.getOrderId() + " da giao thanh cong!");
    }

    @Override
    public String getStateName() {
        return "Da giao";
    }
}
