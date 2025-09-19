package pos.service;

import pos.model.Order;
import pos.discount.Discount;

/**
 * Small manager that encapsulates discount application logic.
 * This extracts discount responsibility out of OrderService (SRP).
 */
public class DiscountManager {
    public void applyDiscount(Order o, Discount d){
        if (o == null) return;
        if (d == null){
            o.setDiscountAmount(0.0);
            o.setDiscountType("NONE");
        } else {
            double amt = d.apply(o);
            o.setDiscountAmount(amt);
            o.setDiscountType(d.getLabel());
        }
    }
}