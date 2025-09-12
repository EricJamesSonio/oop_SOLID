package POS.Orders.Base;

public abstract class Discount {
    private String name;
    private double discountPercent;

    public Discount (String name, double discountPercent) {
        this.name = name;
        this.discountPercent = discountPercent;
    }

    public String getName() {
        return name;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public abstract double applyDiscount(double total);
}


