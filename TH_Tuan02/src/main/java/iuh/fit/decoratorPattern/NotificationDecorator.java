package iuh.fit.decoratorPattern;

public class NotificationDecorator extends OrderDecorator {
    public NotificationDecorator(OrderComponent wrapped) {
        super(wrapped);
    }

    @Override
    public void process(DecoratorOrder order) {
        super.process(order);

        if (order.isCancelled()) {
            System.out.println("[NOTIFY] Gui thong bao huy den khach hang " + order.getCustomerName() + ".");
            return;
        }

        if (order.isDelivered()) {
            System.out.println("[NOTIFY] Gui thong bao giao hang thanh cong.");
        } else {
            System.out.println("[NOTIFY] Gui thong bao don hang dang duoc xu ly.");
        }
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " -> Thong bao";
    }
}
