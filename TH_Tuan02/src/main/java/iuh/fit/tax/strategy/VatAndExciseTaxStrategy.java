package iuh.fit.tax.strategy;

public class VatAndExciseTaxStrategy implements ProductTaxStrategy {
    private static final double VAT_RATE = 0.10;
    private static final double EXCISE_RATE = 0.15;

    @Override
    public double calculateTax(TaxableProduct product) {
        return product.getBasePrice() * (VAT_RATE + EXCISE_RATE);
    }

    @Override
    public String getName() {
        return "VAT 10% + Excise 15%";
    }
}
