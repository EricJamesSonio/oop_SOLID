package POS.Orders.Services;
import POS.Orders.Base.Discount;
import java.util.List;

public class DiscountService implements DiscountCalculator {
    private final List<Discount> discounts;

    public DiscountService(List<Discount> discounts) {
        this.discounts = discounts;
    }

    @Override
    public double calculateDiscount(double subTotal) {
        double totalDiscount = 0.0;
        for (Discount discount : discounts) {
            totalDiscount += discount.applyDiscount(subTotal);
        }
        return totalDiscount;
    }
}
