package iuh.fit.decoratorPattern;

public interface OrderComponent {
    void process(DecoratorOrder order);

    String getDescription();
}
