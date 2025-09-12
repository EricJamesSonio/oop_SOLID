package POS.Orders.Discounts;

import POS.Orders.Base.CustomerType;
import POS.Orders.Base.Discount;
//import POS.Orders.Discounts.SeniorDiscount;

public class SeniorDiscountRule implements DiscountRule {
    @Override
    public boolean appliesTo(CustomerType customerType) {
        return customerType.getIsSenior();
    }

    @Override
    public Discount createDiscount() {
        return new SeniorDiscount("Senior Discount", 0.10);
    }
}
