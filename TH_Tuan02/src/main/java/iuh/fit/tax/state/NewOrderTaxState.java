package iuh.fit.tax.state;

public class NewOrderTaxState implements OrderTaxState {
    @Override
    public double calculateTax(TaxOrder order) {
        System.out.println("[NEW] Don moi tao, chua tinh thue chinh thuc.");
        return 0;
    }

    @Override
    public void next(TaxOrder order) {
        order.setState(new TaxableOrderState());
    }

    @Override
    public String getStateName() {
        return "NEW";
    }
}
