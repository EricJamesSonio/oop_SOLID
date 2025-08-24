package POS;

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

class PWDDiscount extends Discount {
    public PWDDiscount (String name, double discountPercent) {
        super(name, discountPercent);
    }
    @Override
    public double applyDiscount(double total) {
        return total * getDiscountPercent();
    }
}

class SeniorDiscount extends Discount {
    public SeniorDiscount (String name, double discountPercent) {
        super(name, discountPercent);
    }
    @Override
    public double applyDiscount(double total) {
        return total * getDiscountPercent();
    }
}