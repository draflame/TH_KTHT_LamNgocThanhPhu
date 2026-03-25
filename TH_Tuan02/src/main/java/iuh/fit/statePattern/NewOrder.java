package iuh.fit.statePattern;

public class NewOrder implements OrderState {
    @Override
    public void handle(Order order) {
        System.out.println("[MOI TAO] Kiem tra thong tin don hang ID: " + order.getOrderId());
        order.setState(new ProcessingOrder());
    }

    @Override
    public String getStateName() {
        return "Moi tao";
    }
}
