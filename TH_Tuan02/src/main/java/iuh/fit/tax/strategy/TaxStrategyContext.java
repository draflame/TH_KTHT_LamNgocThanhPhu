package iuh.fit.tax.strategy;

public class TaxStrategyContext {
    private ProductTaxStrategy strategy;

    public TaxStrategyContext(ProductTaxStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(ProductTaxStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculateTax(TaxableProduct product) {
        if (strategy == null) {
            throw new IllegalStateException("Chua chon tax strategy");
        }

        return strategy.calculateTax(product);
    }

    public String getCurrentStrategyName() {
        return strategy == null ? "N/A" : strategy.getName();
    }
}
