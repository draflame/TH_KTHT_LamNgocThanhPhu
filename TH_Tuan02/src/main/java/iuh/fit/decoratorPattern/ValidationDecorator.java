package iuh.fit.decoratorPattern;

public class ValidationDecorator extends OrderDecorator {
    public ValidationDecorator(OrderComponent wrapped) {
        super(wrapped);
    }

    @Override
    public void process(DecoratorOrder order) {
        if (order.getOrderId() == null || order.getOrderId().isBlank()) {
            System.out.println("[VALIDATE] Don hang khong hop le: thieu ma don.");
            order.markCancelled();
            return;
        }

        if (order.getCustomerName() == null || order.getCustomerName().isBlank()) {
            System.out.println("[VALIDATE] Don hang khong hop le: thieu ten khach hang.");
            order.markCancelled();
            return;
        }

        if (order.getAmount() <= 0) {
            System.out.println("[VALIDATE] Don hang khong hop le: so tien phai > 0.");
            order.markCancelled();
            return;
        }

        System.out.println("[VALIDATE] Don hang hop le.");
        super.process(order);
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " -> Kiem tra hop le";
    }
}
