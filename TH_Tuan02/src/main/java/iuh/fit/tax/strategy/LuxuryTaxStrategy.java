package iuh.fit.tax.strategy;

public class LuxuryTaxStrategy implements ProductTaxStrategy {
    private static final double VAT_RATE = 0.10;
    private static final double EXCISE_RATE = 0.15;
    private static final double LUXURY_RATE = 0.20;

    @Override
    public double calculateTax(TaxableProduct product) {
        return product.getBasePrice() * (VAT_RATE + EXCISE_RATE + LUXURY_RATE);
    }

    @Override
    public String getName() {
        return "VAT 10% + Excise 15% + Luxury 20%";
    }
}
