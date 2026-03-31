package iuh.fit.payment.decorator;

public class DecoratorPaymentDemo {
    public static void main(String[] args) {
        System.out.println("=== DECORATOR PATTERN: PAYMENT FEATURES COMPOSITION ===\n");

        // Scenario 1: Credit card + processing fee
        PaymentComponent payment1 = new ProcessingFeeDecorator(
                new BasePaymentComponent("Credit Card", "Nguyen Van A", 1000000));
        processPayment("Scenario 1", payment1);

        // Scenario 2: PayPal + discount code
        PaymentComponent payment2 = new DiscountCodeDecorator(
                new BasePaymentComponent("PayPal", "Tran Thi B", 2000000),
                "SAVE10", 0.10);
        processPayment("Scenario 2", payment2);

        // Scenario 3: Credit card + discount + processing fee + insurance
        PaymentComponent payment3 = new PaymentInsuranceDecorator(
                new ProcessingFeeDecorator(
                        new DiscountCodeDecorator(
                                new BasePaymentComponent("Credit Card", "Le Van C", 5000000),
                                "VIP15", 0.15)));
        processPayment("Scenario 3", payment3);
    }

    private static void processPayment(String scenario, PaymentComponent payment) {
        System.out.println("--- " + scenario + " ---");
        System.out.println("Description: " + payment.getDescription());

        if (payment.executePayment()) {
            System.out.printf("Final Amount: %.0f VND%n", payment.getFinalAmount());
            System.out.println("Status: ✓ SUCCESS");
        } else {
            System.out.println("Status: ✗ FAILED");
        }

        System.out.println("---------------------------------------------\n");
    }
}
