package POS.Orders.Discounts;
import POS.Orders.Base.Discount;

class PWDDiscount extends Discount {
    public PWDDiscount (String name, double discountPercent) {
        super(name, discountPercent);
    }
    @Override
    public double applyDiscount(double total) {
        return total * getDiscountPercent();
    }
}
