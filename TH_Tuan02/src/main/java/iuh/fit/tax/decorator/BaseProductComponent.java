package iuh.fit.tax.decorator;

public class BaseProductComponent implements TaxComponent {
    private final String name;
    private final double basePrice;

    public BaseProductComponent(String name, double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
    }

    public double getBasePrice() {
        return basePrice;
    }

    @Override
    public double totalPrice() {
        return basePrice;
    }

    @Override
    public String breakdown() {
        return name + " (base: " + Math.round(basePrice) + ")";
    }
}
