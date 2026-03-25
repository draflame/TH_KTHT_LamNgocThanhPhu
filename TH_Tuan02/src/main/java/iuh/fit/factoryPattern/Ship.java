package iuh.fit.factoryPattern;

public class Ship implements Transport {
    @Override
    public void deliver() {
        System.out.println("Giao hang bang duong bien (Ship).");
    }
}
