package iuh.fit.tax.state;

public interface OrderTaxState {
    double calculateTax(TaxOrder order);

    void next(TaxOrder order);

    String getStateName();
}
