package pos.discount;

import pos.model.Order;

public class PWDDiscount implements Discount {
    private final double rate; // e.g. 0.12

    public PWDDiscount(double rate) {
        this.rate = rate / 100.0; // store as decimal
    }

    @Override
    public double apply(Order order) {
        if (order == null) return 0.0;
        return Math.round(order.getSubtotal() * rate * 100.0) / 100.0;
    }

    @Override
    public String getLabel() {
        return "PWD (" + (int)(rate * 100) + "%)";
    }

    @Override
    public double getRate() {
        return rate;
    }
}
