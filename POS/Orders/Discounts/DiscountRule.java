package POS.Orders.Discounts;
import POS.Orders.Base.CustomerType;
import POS.Orders.Base.Discount;

public interface DiscountRule {
    boolean appliesTo(CustomerType customerType);
    Discount createDiscount();
}


