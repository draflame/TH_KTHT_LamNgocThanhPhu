package iuh.fit.payment.strategy;

import java.util.List;

public class StrategyPaymentDemo {
    public static void main(String[] args) {
        List<Payment> payments = List.of(
                new Payment("TRX001", "Nguyen Van A", 1000000, PaymentMethod.CREDIT_CARD),
                new Payment("TRX002", "Tran Thi B", 5000000, PaymentMethod.PAYPAL),
                new Payment("TRX003", "Le Van C", 10000000, PaymentMethod.BANK_TRANSFER));

        PaymentStrategyContext context = new PaymentStrategyContext(new CreditCardPaymentStrategy());

        System.out.println("=== STRATEGY PATTERN: PAYMENT METHOD SELECTION ===\n");
        for (Payment payment : payments) {
            context.setStrategy(selectStrategy(payment.getMethod()));

            System.out.println("Transaction: " + payment.getTransactionId());
            System.out.println("Customer: " + payment.getCustomer());
            System.out.println("Payment Method: " + context.getStrategyName());
            System.out.printf("Original Amount: %.0f%n", payment.getAmount());

            double fee = context.calculateTotalAmount(payment) - payment.getAmount();
            double total = context.calculateTotalAmount(payment);

            System.out.printf("Processing Fee: %.0f%n", fee);
            System.out.printf("Total Amount: %.0f%n", total);

            if (context.pay(payment)) {
                System.out.println("Status: ✓ SUCCESS");
            } else {
                System.out.println("Status: ✗ FAILED");
            }

            System.out.println("---------------------------------------------\n");
        }
    }

    private static PaymentStrategy selectStrategy(PaymentMethod method) {
        return switch (method) {
            case CREDIT_CARD -> new CreditCardPaymentStrategy();
            case PAYPAL -> new PayPalPaymentStrategy();
            case BANK_TRANSFER -> new BankTransferPaymentStrategy();
        };
    }
}
