package iuh.fit;

import iuh.fit.singletonPattern.Calculator;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        showWelcome();
        showMainMenu();
    }

    private static void showWelcome() {
        System.out.println("==============================================");
        System.out.println("          DESIGN PATTERNS DEMO SYSTEM        ");
        System.out.println("              Chao mung ban den!              ");
        System.out.println("==============================================");
        System.out.println();
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("----------------------------------------------");
            System.out.println("             CHON DESIGN PATTERN             ");
            System.out.println("----------------------------------------------");
            System.out.println("1. Singleton Pattern                         ");
            System.out.println("2. Factory Pattern                           ");
            System.out.println("3. State Pattern                             ");
            System.out.println("4. Strategy Pattern                          ");
            System.out.println("5. Decorator Pattern                         ");
            System.out.println("0. Thoat                                     ");
            System.out.println("----------------------------------------------");
            System.out.print("Chon pattern (0-5): ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    handleSingletonPattern();
                    break;
                case 2:
                    handleFactoryPattern();
                    break;
                case 3:
                    handleStatePattern();
                    break;
                case 4:
                    handleStrategyPattern();
                    break;
                case 5:
                    handleDecoratorPattern();
                    break;
                case 0:
                    System.out.println("\nCam on ban da su dung! Tam biet!");
                    return;
                default:
                    System.out.println("Lua chon khong hop le! Vui long chon tu 0-5.");
            }

            System.out.println("\n" + "=".repeat(50));
        }
    }

    private static void handleSingletonPattern() {
        System.out.println("\n==============================================");
        System.out.println("              SINGLETON PATTERN              ");
        System.out.println("==============================================");

        System.out.println("MO TA:");
        System.out.println("   • Singleton dam bao chi co DUY NHAT 1 instance cua class");
        System.out.println("   • Vi du: Calculator - chi co 1 may tinh, luu ket qua lien tuc");
        System.out.println("   • Khi goi getInstance() nhieu lan van tra ve cung 1 object");
        System.out.println();

        System.out.println("HE THONG: CALCULATOR");
        System.out.println("   • May tinh singleton luu ket qua giua cac phep tinh");
        System.out.println("   • Ho tro: +, -, *, /, clear, xem ket qua");
        System.out.println();

        runCalculatorDemo();
    }

    private static void runCalculatorDemo() {
        Calculator calc = Calculator.getInstance();

        while (true) {
            System.out.println("----------------------------------------------");
            System.out.println("              CALCULATOR MENU                ");
            System.out.println("----------------------------------------------");
            System.out.printf("Ket qua hien tai: %.2f%n", calc.getResult());
            System.out.println("----------------------------------------------");
            System.out.println("1. Cong                                      ");
            System.out.println("2. Tru                                       ");
            System.out.println("3. Nhan                                      ");
            System.out.println("4. Chia                                      ");
            System.out.println("5. Clear (Xoa ket qua)                      ");
            System.out.println("6. Xem thong tin chi tiet                   ");
            System.out.println("7. Test tinh Singleton                      ");
            System.out.println("0. Quay lai menu chinh                      ");
            System.out.println("----------------------------------------------");
            System.out.print("Chon thao tac (0-7): ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    performCalculation(calc, "add");
                    break;
                case 2:
                    performCalculation(calc, "subtract");
                    break;
                case 3:
                    performCalculation(calc, "multiply");
                    break;
                case 4:
                    performCalculation(calc, "divide");
                    break;
                case 5:
                    calc.clear();
                    System.out.println("Da xoa ket qua!");
                    break;
                case 6:
                    calc.showInfo();
                    break;
                case 7:
                    testSingleton();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }

            System.out.println("\nKet qua moi: " + calc.getResult());
            System.out.println("Press Enter de tiep tuc...");
            scanner.nextLine();
        }
    }

    private static void performCalculation(Calculator calc, String operation) {
        System.out.print("Nhap so: ");
        double number = getDoubleInput();

        switch (operation) {
            case "add":
                calc.add(number);
                System.out.println("Da cong " + number);
                break;
            case "subtract":
                calc.subtract(number);
                System.out.println("Da tru " + number);
                break;
            case "multiply":
                calc.multiply(number);
                System.out.println("Da nhan " + number);
                break;
            case "divide":
                if (number != 0) {
                    calc.divide(number);
                    System.out.println("Da chia " + number);
                } else {
                    System.out.println("Khong the chia cho 0!");
                }
                break;
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
            System.out.println("SINGLETON HOAT DONG DUNG - Chi co 1 instance!");
        } else {
            System.out.println("Co loi trong Singleton implementation!");
        }

        System.out.println("\nDia chi bo nho:");
        System.out.println("calc1: " + calc1.hashCode());
        System.out.println("calc2: " + calc2.hashCode());
        System.out.println("calc3: " + calc3.hashCode());
    }

    // Cac method cho cac pattern khac (tam thoi)
    private static void handleFactoryPattern() {
        System.out.println("\nFACTORY PATTERN");
        System.out.println("Chuc nang dang duoc phat trien...");
        System.out.println("Press Enter de quay lai...");
        scanner.nextLine();
    }

    private static void handleStatePattern() {
        System.out.println("\nSTATE PATTERN");
        System.out.println("Chuc nang dang duoc phat trien...");
        System.out.println("Press Enter de quay lai...");
        scanner.nextLine();
    }

    private static void handleStrategyPattern() {
        System.out.println("\nSTRATEGY PATTERN");
        System.out.println("Chuc nang dang duoc phat trien...");
        System.out.println("Press Enter de quay lai...");
        scanner.nextLine();
    }

    private static void handleDecoratorPattern() {
        System.out.println("\nDECORATOR PATTERN");
        System.out.println("Chuc nang dang duoc phat trien...");
        System.out.println("Press Enter de quay lai...");
        scanner.nextLine();
    }


    // Utility methods
    private static int getIntInput() {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine().trim());
                return input;
            } catch (NumberFormatException e) {
                System.out.print("Vui long nhap so nguyen hop le: ");
            }
        }
    }

    private static double getDoubleInput() {
        while (true) {
            try {
                double input = Double.parseDouble(scanner.nextLine().trim());
                return input;
            } catch (NumberFormatException e) {
                System.out.print("Vui long nhap so hop le: ");
            }
        }
    }
}