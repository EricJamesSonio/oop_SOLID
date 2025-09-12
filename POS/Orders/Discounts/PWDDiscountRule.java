package POS.Orders.Discounts;

import POS.Orders.Base.CustomerType;
import POS.Orders.Base.Discount;
//import POS.Orders.Discounts.PWDDiscount;

public class PWDDiscountRule implements DiscountRule {
    @Override
    public boolean appliesTo(CustomerType customerType) {
        return customerType.getIsPWD();
    }

    @Override
    public Discount createDiscount() {
        return new PWDDiscount("PWD Discount", 0.12);
    }
}
