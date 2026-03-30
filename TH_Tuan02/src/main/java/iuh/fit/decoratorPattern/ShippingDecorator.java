package iuh.fit.decoratorPattern;

public class ShippingDecorator extends OrderDecorator {
    public ShippingDecorator(OrderComponent wrapped) {
        super(wrapped);
    }

    @Override
    public void process(DecoratorOrder order) {
        super.process(order);

        if (order.isCancelled()) {
            System.out.println("[SHIPPING] Don hang da huy, khong giao hang.");
            return;
        }

        order.markDelivered();
        System.out.println("[SHIPPING] Da giao don hang " + order.getOrderId() + ".");
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " -> Giao hang";
    }
}
