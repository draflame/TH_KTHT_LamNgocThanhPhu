package iuh.fit;

import iuh.fit.factoryPattern.Logistics;
import iuh.fit.factoryPattern.RoadLogistics;
import iuh.fit.factoryPattern.SeaLogistics;
import iuh.fit.singletonPattern.Calculator;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Logistics logistics = new RoadLogistics();
        logistics.planDelivery();

        logistics = new SeaLogistics();
        logistics.planDelivery();
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
        showNotReady("STATE PATTERN");
    }

    private static void handleStrategyPattern() {
        showNotReady("STRATEGY PATTERN");
    }

    private static void handleDecoratorPattern() {
        showNotReady("DECORATOR PATTERN");
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