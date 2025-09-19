package pos.discount;

import pos.model.Order;

public class CombinedDiscount implements Discount {
    private final Discount d1;
    private final Discount d2;

    public CombinedDiscount(Discount d1, Discount d2) {
        this.d1 = d1;
        this.d2 = d2;
    }

    @Override
    public double apply(Order order) {
        if (order == null) return 0.0;
        double subtotal = order.getSubtotal();
        double afterFirst = subtotal - d1.apply(order);
        double secondDiscount = Math.round(afterFirst * d2.getRate() * 100.0) / 100.0;

        return d1.apply(order) + secondDiscount;
    }

    @Override
    public String getLabel() {
        return d1.getLabel() + " + " + d2.getLabel();
    }

    @Override
    public double getRate() {
        // Effective sequential rate (not simple sum)
        return 1 - (1 - d1.getRate()) * (1 - d2.getRate());
    }
}
