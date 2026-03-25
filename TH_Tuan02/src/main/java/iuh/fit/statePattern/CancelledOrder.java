package iuh.fit.statePattern;

public class CancelledOrder implements OrderState {
    @Override
    public void handle(Order order) {
        System.out.println(
                "[HUY] Don hang da bi huy. Hoan tien " + order.getAmount() + " VND cho " + order.getCustomerName());
    }

    @Override
    public String getStateName() {
        return "Huy";
    }
}
