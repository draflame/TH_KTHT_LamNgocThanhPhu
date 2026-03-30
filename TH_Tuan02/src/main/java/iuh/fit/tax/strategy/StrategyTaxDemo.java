package iuh.fit.tax.strategy;

import java.util.List;

public class StrategyTaxDemo {
    public static void main(String[] args) {
        List<TaxableProduct> products = List.of(
                new TaxableProduct("Sach giao khoa", 120000, ProductType.NORMAL),
                new TaxableProduct("Nuoc ngot", 30000, ProductType.EXCISE),
                new TaxableProduct("Dong ho cao cap", 15000000, ProductType.LUXURY));

        TaxStrategyContext context = new TaxStrategyContext(new VatOnlyTaxStrategy());

        System.out.println("=== STRATEGY PATTERN: TINH THUE SAN PHAM ===");
        for (TaxableProduct product : products) {
            context.setStrategy(selectStrategy(product));
            double tax = context.calculateTax(product);
            double total = product.getBasePrice() + tax;

            System.out.println("San pham: " + product.getName());
            System.out.println("Loai: " + product.getType());
            System.out.println("Chien luoc thue: " + context.getCurrentStrategyName());
            System.out.printf("Gia goc: %.0f | Thue: %.0f | Tong: %.0f%n", product.getBasePrice(), tax, total);
            System.out.println("---------------------------------------------");
        }
    }

    private static ProductTaxStrategy selectStrategy(TaxableProduct product) {
        return switch (product.getType()) {
            case NORMAL -> new VatOnlyTaxStrategy();
            case EXCISE -> new VatAndExciseTaxStrategy();
            case LUXURY -> new LuxuryTaxStrategy();
        };
    }
}
