package iuh.fit.tax.state;

public class ExemptOrderTaxState implements OrderTaxState {
    @Override
    public double calculateTax(TaxOrder order) {
        return 0;
    }

    @Override
    public void next(TaxOrder order) {
        order.setState(new FinalizedTaxState());
    }

    @Override
    public String getStateName() {
        return "EXEMPT";
    }
}
