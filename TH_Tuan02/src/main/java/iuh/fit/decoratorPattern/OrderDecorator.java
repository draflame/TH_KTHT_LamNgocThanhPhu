package iuh.fit.decoratorPattern;

public abstract class OrderDecorator implements OrderComponent {
    protected final OrderComponent wrapped;

    protected OrderDecorator(OrderComponent wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void process(DecoratorOrder order) {
        wrapped.process(order);
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription();
    }
}
