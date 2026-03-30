package iuh.fit.tax.state;

public class FinalizedTaxState implements OrderTaxState {
    @Override
    public double calculateTax(TaxOrder order) {
        return order.getLastTax();
    }

    @Override
    public void next(TaxOrder order) {
        System.out.println("[FINALIZED] Don hang da chot, khong chuyen trang thai them.");
    }

    @Override
    public String getStateName() {
        return "FINALIZED";
    }
}
