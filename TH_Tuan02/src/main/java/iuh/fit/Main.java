package iuh.fit;

import iuh.fit.decoratorPattern.BasicOrderComponent;
import iuh.fit.decoratorPattern.CancelAndRefundDecorator;
import iuh.fit.decoratorPattern.DecoratorOrder;
import iuh.fit.decoratorPattern.NotificationDecorator;
import iuh.fit.decoratorPattern.OrderComponent;
import iuh.fit.decoratorPattern.PackingDecorator;
import iuh.fit.decoratorPattern.ShippingDecorator;
import iuh.fit.decoratorPattern.ValidationDecorator;
import iuh.fit.singletonPattern.Calculator;
import iuh.fit.statePattern.Order;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        DecoratorOrder order1 = new DecoratorOrder("DCR001", "Nguyen Van A", 500000);
        OrderComponent successFlow = new NotificationDecorator(
                new ShippingDecorator(
                        new PackingDecorator(
                                new ValidationDecorator(
                                        new BasicOrderComponent()))));

        System.out.println("Chuoi decorator (success): " + successFlow.getDescription());
        order1.displayInfo();
        successFlow.process(order1);
        order1.displayInfo();

        System.out.println("--------------------------------------\n");

        DecoratorOrder order2 = new DecoratorOrder("DCR002", "Tran Thi B", 300000);
        OrderComponent cancelFlow = new NotificationDecorator(
                new CancelAndRefundDecorator(
                        new ValidationDecorator(
                                new BasicOrderComponent())));

        System.out.println("Chuoi decorator (cancel): " + cancelFlow.getDescription());
        order2.displayInfo();
        cancelFlow.process(order2);
        order2.displayInfo();
    }

    private static void showWelcome() {
        System.out.println("==============================================");
        System.out.println("          HE THONG DEMO DESIGN PATTERN       ");
        System.out.println("            Chao mung ban den!               ");
        System.out.println("==============================================");
        System.out.println();
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("----------------------------------------------");
            System.out.println("             CHON DESIGN PATTERN             ");
            System.out.println("----------------------------------------------");
            System.out.println("1. Singleton Pattern  (singleton, single)    ");
            System.out.println("2. Factory Pattern    (factory)              ");
            System.out.println("3. State Pattern      (state)                ");
            System.out.println("4. Strategy Pattern   (strategy)             ");
            System.out.println("5. Decorator Pattern  (decorator)            ");
            System.out.println("0. Thoat                                     ");
            System.out.println("----------------------------------------------");

            System.out.print("Lua chon [0-5 hoac tu khoa]: ");

            String choice = readChoice();

            if (matches(choice, "1", "singleton", "single")) {
                handleSingletonPattern();
            } else if (matches(choice, "2", "factory")) {
                handleFactoryPattern();
            } else if (matches(choice, "3", "state")) {
                handleStatePattern();
            } else if (matches(choice, "4", "strategy")) {
                handleStrategyPattern();
            } else if (matches(choice, "5", "decorator")) {
                handleDecoratorPattern();
            } else if (matches(choice, "0", "exit", "quit", "thoat")) {
                System.out.println("\nCam on ban da su dung. Tam biet!");
                return;
            } else {
                System.out.println("Lua chon khong hop le. Vui long chon theo menu.");
            }

            System.out.println("\n" + "=".repeat(50));
        }
    }

    private static void handleSingletonPattern() {
        System.out.println("\n==============================================");
        System.out.println("              SINGLETON PATTERN              ");
        System.out.println("==============================================");

        System.out.println("MO TA:");
        System.out.println(" - Singleton dam bao chi co DUY NHAT 1 instance cua class");
        System.out.println(" - Vi du: Calculator chi co 1 may tinh, luu ket qua lien tuc");
        System.out.println(" - Goi getInstance() nhieu lan van tra ve cung 1 object");
        System.out.println();
        System.out.println("HE THONG: CALCULATOR");
        System.out.println(" - Ho tro: +, -, *, /, clear, xem ket qua");
        System.out.println(" - Ket qua duoc giu lai qua cac thao tac");
        System.out.println();

        runCalculatorDemo();
    }

    private static void runCalculatorDemo() {
        Calculator calc = Calculator.getInstance();

        while (true) {
            System.out.println("----------------------------------------------");
            System.out.println("               MENU MAY TINH                 ");
            System.out.println("----------------------------------------------");
            System.out.printf("Ket qua hien tai: %.4f%n", calc.getResult());
            System.out.println("----------------------------------------------");
            System.out.println("1. Cong        (+, cong, add)                ");
            System.out.println("2. Tru         (-, tru, sub)                 ");
            System.out.println("3. Nhan        (*, nhan, mul)                ");
            System.out.println("4. Chia        (/, chia, div)                ");
            System.out.println("5. Xoa ket qua                                ");
            System.out.println("6. Xem thong tin chi tiet                     ");
            System.out.println("7. Kiem tra tinh Singleton                    ");
            System.out.println("0. Quay lai menu chinh                        ");
            System.out.println("----------------------------------------------");

            System.out.print("Thao tac [0-7 hoac tu khoa]: ");
            String choice = readChoice();

            if (matches(choice, "1", "+", "add", "cong")) {
                performCalculation(calc, "add");
            } else if (matches(choice, "2", "-", "subtract", "sub", "tru")) {
                performCalculation(calc, "subtract");
            } else if (matches(choice, "3", "*", "x", "multiply", "mul", "nhan")) {
                performCalculation(calc, "multiply");
            } else if (matches(choice, "4", "/", "divide", "div", "chia")) {
                performCalculation(calc, "divide");
            } else if (matches(choice, "5", "clear", "reset", "xoa")) {
                calc.clear();
                printResult(calc, "Da xoa ket qua.");
            } else if (matches(choice, "6", "info", "detail", "thongtin")) {
                calc.showInfo();
                printResult(calc, "Da hien thi thong tin chi tiet.");
            } else if (matches(choice, "7", "test", "singletontest")) {
                testSingleton();
                printResult(calc, "Da hoan tat kiem tra Singleton.");
            } else if (matches(choice, "0", "back", "menu", "quaylai")) {
                return;
            } else {
                System.out.println("Thao tac khong hop le.");
            }
        }
    }

    private static void performCalculation(Calculator calc, String operation) {
        System.out.print("Nhap so: ");
        double number = getDoubleInput();

        switch (operation) {
            case "add":
                calc.add(number);
                printResult(calc, "Da cong " + number);
                break;
            case "subtract":
                calc.subtract(number);
                printResult(calc, "Da tru " + number);
                break;
            case "multiply":
                calc.multiply(number);
                printResult(calc, "Da nhan " + number);
                break;
            case "divide":
                if (number != 0) {
                    calc.divide(number);
                    printResult(calc, "Da chia " + number);
                } else {
                    System.out.println("Khong the chia cho 0.");
                }
                break;
            default:
                System.out.println("Phep tinh khong hop le.");
        }
    }

    private static void testSingleton() {
        System.out.println("\nKIEM TRA TINH SINGLETON:");

        Calculator calc1 = Calculator.getInstance();
        Calculator calc2 = Calculator.getInstance();
        Calculator calc3 = Calculator.getInstance();

        System.out.println("calc1 == calc2: " + (calc1 == calc2));
        System.out.println("calc2 == calc3: " + (calc2 == calc3));
        System.out.println("calc1 == calc3: " + (calc1 == calc3));

        if (calc1 == calc2 && calc2 == calc3) {
            System.out.println("Singleton hoat dong dung - chi co 1 instance.");
        } else {
            System.out.println("Co loi trong Singleton implementation.");
        }

        System.out.println("\nDia chi bo nho:");
        System.out.println("calc1: " + calc1.hashCode());
        System.out.println("calc2: " + calc2.hashCode());
        System.out.println("calc3: " + calc3.hashCode());
    }

    private static void handleFactoryPattern() {
        showNotReady("FACTORY PATTERN");
    }

    private static void handleStatePattern() {
        System.out.println("\n==============================================");
        System.out.println("              STATE PATTERN                 ");
        System.out.println("==============================================");

        System.out.println("MO TA:");
        System.out.println(" - State Pattern cho phep doi hanh vi cua doi tuong dua vao trang thai");
        System.out.println(" - Vi du: Quan ly don hang voi cac trang thai khac nhau");
        System.out.println(" - Moi trang thai co hanh vi rieng (check info, pack, ship, cancel)");
        System.out.println();
        System.out.println("HE THONG: QUAN LY DON HANG");
        System.out.println(" - Trang thai: Moi tao -> Dang xu ly -> Da giao");
        System.out.println(" - Co the huy don hang o bat ky trang thai nao");
        System.out.println();

        runOrderDemo();
    }

    private static void runOrderDemo() {
        while (true) {
            System.out.println("----------------------------------------------");
            System.out.println("        MENU QUAN LY DON HANG - STATE       ");
            System.out.println("----------------------------------------------");
            System.out.println("1. Tao don hang moi                           ");
            System.out.println("2. Xem tat ca don hang (demo co san)         ");
            System.out.println("0. Quay lai menu chinh                        ");
            System.out.println("----------------------------------------------");

            System.out.print("Lua chon: ");
            String choice = readChoice();

            if (matches(choice, "1", "tao")) {
                createNewOrder();
            } else if (matches(choice, "2", "xem", "demo")) {
                demoOrderStates();
            } else if (matches(choice, "0", "back", "quaylai")) {
                return;
            } else {
                System.out.println("Lua chon khong hop le.");
            }
        }
    }

    private static void createNewOrder() {
        System.out.print("Nhap ID don hang: ");
        String orderId = readChoice();

        System.out.print("Nhap ten khach hang: ");
        String customerName = readChoice();

        System.out.print("Nhap so tien: ");
        double amount = getDoubleInput();

        Order order = new Order(orderId, customerName, amount);
        order.displayInfo();

        int step = 1;
        while (true) {
            System.out.println("----------------------------------------------");
            System.out.println("        MENU XU LY DON HANG - BUOC " + step + "     ");
            System.out.println("----------------------------------------------");
            System.out.println("1. Tien hanh buoc tiep theo");
            System.out.println("2. Xem thong tin don hang");
            System.out.println("3. Huy don hang");
            System.out.println("0. Thoat");
            System.out.println("----------------------------------------------");

            System.out.print("Lua chon: ");
            String choice = readChoice();

            if (matches(choice, "1", "tien")) {
                order.processOrder();
                step++;
                if (step > 3) {
                    System.out.println(">>> Don hang hoat dong hoan tat <<<\n");
                    break;
                }
            } else if (matches(choice, "2", "xem", "info")) {
                order.displayInfo();
            } else if (matches(choice, "3", "huy")) {
                order.cancelOrder();
                break;
            } else if (matches(choice, "0", "exit", "thoat")) {
                break;
            } else {
                System.out.println("Lua chon khong hop le.");
            }
        }
    }

    private static void demoOrderStates() {
        System.out.println("\n=== DEMO 1: Don hang hop le ===\n");

        Order order1 = new Order("ORD001", "Nguyen Van A", 500000);
        order1.displayInfo();
        order1.processOrder();
        order1.processOrder();
        order1.displayInfo();

        System.out.println("\n=== DEMO 2: Don hang bi huy ===\n");

        Order order2 = new Order("ORD002", "Tran Thi B", 300000);
        order2.displayInfo();
        order2.processOrder();
        order2.cancelOrder();
        order2.displayInfo();

        pause();
    }

    private static void handleStrategyPattern() {
        showNotReady("STRATEGY PATTERN");
    }

    private static void handleDecoratorPattern() {
        System.out.println("\n==============================================");
        System.out.println("             DECORATOR PATTERN               ");
        System.out.println("==============================================");

        System.out.println("MO TA:");
        System.out.println(" - Decorator cho phep mo rong xu ly don hang theo tung lop.");
        System.out.println(" - Co the ket hop dong: validate -> core -> packing -> shipping -> notify.");
        System.out.println(" - Ho tro luong huy don va hoan tien ma khong sua lop core.");
        System.out.println();

        runDecoratorDemo();
    }

    private static void runDecoratorDemo() {
        while (true) {
            System.out.println("----------------------------------------------");
            System.out.println("       MENU DECORATOR - ORDER PROCESSING     ");
            System.out.println("----------------------------------------------");
            System.out.println("1. Demo xu ly don thanh cong                  ");
            System.out.println("2. Demo huy don va hoan tien                  ");
            System.out.println("0. Quay lai menu chinh                        ");
            System.out.println("----------------------------------------------");

            System.out.print("Lua chon: ");
            String choice = readChoice();

            if (matches(choice, "1", "success", "ok")) {
                demoDecoratorSuccess();
            } else if (matches(choice, "2", "cancel", "huy")) {
                demoDecoratorCancelled();
            } else if (matches(choice, "0", "back", "quaylai")) {
                return;
            } else {
                System.out.println("Lua chon khong hop le.");
            }
        }
    }

    private static void demoDecoratorSuccess() {
        DecoratorOrder order = new DecoratorOrder("DCR001", "Le Thi C", 450000);

        OrderComponent flow = new NotificationDecorator(
                new ShippingDecorator(
                        new PackingDecorator(
                                new ValidationDecorator(
                                        new BasicOrderComponent()))));

        System.out.println("Chuoi decorator: " + flow.getDescription());
        order.displayInfo();
        flow.process(order);
        order.displayInfo();
        pause();
    }

    private static void demoDecoratorCancelled() {
        DecoratorOrder order = new DecoratorOrder("DCR002", "Pham Van D", -120000);

        OrderComponent flow = new NotificationDecorator(
                new CancelAndRefundDecorator(
                        new ValidationDecorator(
                                new BasicOrderComponent())));

        System.out.println("Chuoi decorator: " + flow.getDescription());
        order.displayInfo();
        flow.process(order);
        order.displayInfo();
        pause();
    }

    private static void showNotReady(String patternName) {
        System.out.println("\n" + patternName);
        System.out.println("Chuc nang dang duoc phat trien.");
        pause();
    }

    private static void pause() {
        System.out.println("Nhan Enter de tiep tuc...");
        scanner.nextLine();
    }

    private static String readChoice() {
        return scanner.nextLine().trim().toLowerCase();
    }

    private static boolean matches(String input, String... aliases) {
        for (String alias : aliases) {
            if (alias.equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }

    private static void printResult(Calculator calc, String message) {
        System.out.println(message);
        System.out.printf("Ket qua moi: %.4f%n", calc.getResult());
    }

    private static double getDoubleInput() {
        while (true) {
            try {
                String normalized = scanner.nextLine().trim().replace(',', '.');
                return Double.parseDouble(normalized);
            } catch (NumberFormatException e) {
                System.out.print("Vui long nhap so hop le: ");
            }
        }
    }
}