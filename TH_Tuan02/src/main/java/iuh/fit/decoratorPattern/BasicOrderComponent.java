package iuh.fit.decoratorPattern;

public class BasicOrderComponent implements OrderComponent {
    @Override
    public void process(DecoratorOrder order) {
        if (order.isCancelled()) {
            System.out.println("[CORE] Don hang da huy, bo qua xu ly co ban.");
            return;
        }

        System.out.println("[CORE] Tiep nhan don hang " + order.getOrderId() + " cua " + order.getCustomerName() + ".");
    }

    @Override
    public String getDescription() {
        return "Xu ly co ban";
    }
}
