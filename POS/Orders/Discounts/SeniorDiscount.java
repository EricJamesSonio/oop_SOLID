package POS.Orders.Discounts;
import POS.Orders.Base.Discount;

class SeniorDiscount extends Discount {
    public SeniorDiscount (String name, double discountPercent) {
        super(name, discountPercent);
    }
    @Override
    public double applyDiscount(double total) {
        return total * getDiscountPercent();
    }
}