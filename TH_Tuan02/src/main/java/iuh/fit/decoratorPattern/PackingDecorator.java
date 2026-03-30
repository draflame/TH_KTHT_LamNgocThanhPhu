package iuh.fit.decoratorPattern;

public class PackingDecorator extends OrderDecorator {
    public PackingDecorator(OrderComponent wrapped) {
        super(wrapped);
    }

    @Override
    public void process(DecoratorOrder order) {
        super.process(order);

        if (order.isCancelled()) {
            System.out.println("[PACKING] Don hang da huy, bo qua dong goi.");
            return;
        }

        System.out.println("[PACKING] Dong goi don hang " + order.getOrderId() + ".");
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " -> Dong goi";
    }
}
