package iuh.fit.decoratorPattern;

public class CancelAndRefundDecorator extends OrderDecorator {
    public CancelAndRefundDecorator(OrderComponent wrapped) {
        super(wrapped);
    }

    @Override
    public void process(DecoratorOrder order) {
        super.process(order);

        if (order.isDelivered()) {
            System.out.println("[CANCEL] Don hang da giao, khong the huy.");
            return;
        }

        if (order.isCancelled()) {
            System.out.println(
                    "[CANCEL] Xac nhan huy don " + order.getOrderId() + ", hoan tien " + order.getAmount() + " VND.");
            return;
        }

        order.markCancelled();
        System.out.println("[CANCEL] Da huy don " + order.getOrderId() + ", hoan tien " + order.getAmount() + " VND.");
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " -> Huy va hoan tien";
    }
}
