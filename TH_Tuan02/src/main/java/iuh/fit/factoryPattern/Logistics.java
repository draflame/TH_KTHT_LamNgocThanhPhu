package iuh.fit.factoryPattern;

public abstract class Logistics {
    public void planDelivery() {
        Transport transport = createTransport();
        System.out.println("Lap ke hoach giao hang...");
        transport.deliver();
    }

    public abstract Transport createTransport();
}
