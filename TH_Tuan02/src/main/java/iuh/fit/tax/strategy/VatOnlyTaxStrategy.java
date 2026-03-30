package iuh.fit.tax.strategy;

public class VatOnlyTaxStrategy implements ProductTaxStrategy {
    private static final double VAT_RATE = 0.10;

    @Override
    public double calculateTax(TaxableProduct product) {
        return product.getBasePrice() * VAT_RATE;
    }

    @Override
    public String getName() {
        return "VAT 10%";
    }
}
