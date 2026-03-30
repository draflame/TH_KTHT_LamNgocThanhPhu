package iuh.fit.tax.state;

public class StateTaxDemo {
    public static void main(String[] args) {
        System.out.println("=== STATE PATTERN: TINH THUE THEO TRANG THAI DON ===");

        TaxOrder normalOrder = new TaxOrder("S001", new TaxableProduct("Sua tuoi", 45000, ProductType.NORMAL));
        runFlow(normalOrder, false);

        TaxOrder luxuryOrder = new TaxOrder("S002", new TaxableProduct("Tui xach cao cap", 8000000, ProductType.LUXURY));
        runFlow(luxuryOrder, false);

        TaxOrder exemptOrder = new TaxOrder("S003", new TaxableProduct("Hang xuat khau", 1000000, ProductType.EXCISE));
        runFlow(exemptOrder, true);
    }

    private static void runFlow(TaxOrder order, boolean exempt) {
        System.out.println("Don: " + order.getOrderId() + " | State: " + order.getState().getStateName());
        System.out.printf("Thue hien tai: %.0f%n", order.calculateTax());

        if (exempt) {
            order.setState(new ExemptOrderTaxState());
        } else {
            order.moveToNextState();
        }

        System.out.println("Don: " + order.getOrderId() + " | State: " + order.getState().getStateName());
        System.out.printf("Thue hien tai: %.0f%n", order.calculateTax());

        order.moveToNextState();
        System.out.println("Don: " + order.getOrderId() + " | State: " + order.getState().getStateName());
        System.out.printf("Thue da chot: %.0f%n", order.calculateTax());
        System.out.println("---------------------------------------------");
    }
}
