package iuh.fit.payment.state;

public class StatePaymentDemo {
    public static void main(String[] args) {
        System.out.println("=== STATE PATTERN: PAYMENT TRANSACTION LIFECYCLE ===\n");

        // Scenario 1: Successful payment flow
        System.out.println("--- Scenario 1: Successful Payment ---");
        PaymentTransaction success = new PaymentTransaction("TRX001", "Nguyen Van A", 1000000);
        demonstrateFlow(success, true);

        System.out.println("\n--- Scenario 2: Failed Payment with Retry ---");
        PaymentTransaction failed = new PaymentTransaction("TRX002", "Tran Thi B", 500000);
        demonstrateFailedFlow(failed);
    }

    private static void demonstrateFlow(PaymentTransaction transaction, boolean success) {
        System.out.println("Transaction: " + transaction.getTransactionId());
        System.out.println("Customer: " + transaction.getCustomer());
        System.out.println("Amount: " + transaction.getAmount() + " VND");
        System.out.println("Current State: " + transaction.getState().getStateName());
        System.out.println();

        // PENDING -> PROCESSING
        transaction.process();
        System.out.println("Current State: " + transaction.getState().getStateName());
        System.out.println();

        // PROCESSING -> COMPLETED or FAILED
        if (success) {
            transaction.complete();
        } else {
            transaction.fail();
        }
        System.out.println("Current State: " + transaction.getState().getStateName());
    }

    private static void demonstrateFailedFlow(PaymentTransaction transaction) {
        System.out.println("Transaction: " + transaction.getTransactionId());
        System.out.println("Customer: " + transaction.getCustomer());
        System.out.println("Amount: " + transaction.getAmount() + " VND");
        System.out.println("Current State: " + transaction.getState().getStateName());
        System.out.println();

        // PENDING -> PROCESSING
        transaction.process();
        System.out.println("Current State: " + transaction.getState().getStateName());
        System.out.println();

        // PROCESSING -> FAILED
        transaction.fail();
        System.out.println("Current State: " + transaction.getState().getStateName());
        System.out.println();

        // Retry: FAILED -> PENDING
        System.out.println("--- Retrying Payment ---");
        transaction.fail();
        System.out.println("Current State: " + transaction.getState().getStateName());
        System.out.println();

        // Second attempt: PENDING -> PROCESSING -> COMPLETED
        transaction.process();
        System.out.println("Current State: " + transaction.getState().getStateName());
        System.out.println();

        transaction.complete();
        System.out.println("Current State: " + transaction.getState().getStateName());
    }
}
