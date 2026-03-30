package iuh.fit.tax.strategy;

public interface ProductTaxStrategy {
    double calculateTax(TaxableProduct product);

    String getName();
}
