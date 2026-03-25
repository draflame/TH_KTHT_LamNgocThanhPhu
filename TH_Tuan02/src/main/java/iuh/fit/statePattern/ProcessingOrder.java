package iuh.fit.statePattern;

public class ProcessingOrder implements OrderState {
    @Override
    public void handle(Order order) {
        System.out.println("[DANG XU LY] Dong goi va van chuyen hang...");
        order.setState(new ShippedOrder());
    }

    @Override
    public String getStateName() {
        return "Dang xu ly";
    }
}
