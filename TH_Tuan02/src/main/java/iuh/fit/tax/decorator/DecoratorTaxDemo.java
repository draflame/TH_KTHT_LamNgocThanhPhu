package iuh.fit.tax.decorator;

public class DecoratorTaxDemo {
    public static void main(String[] args) {
        System.out.println("=== DECORATOR PATTERN: TINH THUE SAN PHAM ===");

        TaxComponent normal = new VatDecorator(new BaseProductComponent("Sach", 120000));
        printResult("NORMAL", normal);

        TaxComponent excise = new ExciseDecorator(
                new VatDecorator(
                        new BaseProductComponent("Nuoc ngot", 30000)));
        printResult("EXCISE", excise);

        TaxComponent luxury = new LuxuryDecorator(
                new ExciseDecorator(
                        new VatDecorator(
                                new BaseProductComponent("Dong ho xa xi", 15000000))));
        printResult("LUXURY", luxury);
    }

    private static void printResult(String type, TaxComponent component) {
        System.out.println("Loai: " + type);
        System.out.println("Breakdown: " + component.breakdown());
        System.out.printf("Tong gia sau thue: %.0f%n", component.totalPrice());
        System.out.println("---------------------------------------------");
    }
}
