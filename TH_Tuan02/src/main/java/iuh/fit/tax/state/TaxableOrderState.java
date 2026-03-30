package iuh.fit.tax.state;

public class TaxableOrderState implements OrderTaxState {
    private static final double VAT_RATE = 0.10;
    private static final double EXCISE_RATE = 0.15;
    private static final double LUXURY_RATE = 0.20;

    @Override
    public double calculateTax(TaxOrder order) {
        double base = order.getProduct().getBasePrice();
        return switch (order.getProduct().getType()) {
            case NORMAL -> base * VAT_RATE;
            case EXCISE -> base * (VAT_RATE + EXCISE_RATE);
            case LUXURY -> base * (VAT_RATE + EXCISE_RATE + LUXURY_RATE);
        };
    }

    @Override
    public void next(TaxOrder order) {
        order.setState(new FinalizedTaxState());
    }

    @Override
    public String getStateName() {
        return "TAXABLE";
    }
}
