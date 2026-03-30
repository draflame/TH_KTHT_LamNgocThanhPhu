package iuh.fit.tax.strategy;

public class TaxableProduct {
    private final String name;
    private final double basePrice;
    private final ProductType type;

    public TaxableProduct(String name, double basePrice, ProductType type) {
        this.name = name;
        this.basePrice = basePrice;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public ProductType getType() {
        return type;
    }
}
