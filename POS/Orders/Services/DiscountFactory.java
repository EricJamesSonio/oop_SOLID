package POS.Orders.Services;

import POS.Orders.Base.CustomerType;
import POS.Orders.Base.Discount;
import POS.Orders.Discounts.DiscountRule;
import POS.Orders.Discounts.PWDDiscountRule;
import POS.Orders.Discounts.SeniorDiscountRule;

import java.util.ArrayList;
import java.util.List;

public class DiscountFactory {

    public static DiscountCalculator createDiscountCalculator(CustomerType customerType) {
        List<Discount> applicableDiscounts = new ArrayList<>();

        // List of all rules
        List<DiscountRule> rules = List.of(
                new PWDDiscountRule(),
                new SeniorDiscountRule()
                // add more rules here if needed
        );

        for (DiscountRule rule : rules) {
            if (rule.appliesTo(customerType)) {
                applicableDiscounts.add(rule.createDiscount());
            }
        }

        // Return a DiscountService that applies all applicable discounts
        return new DiscountService(applicableDiscounts);
    }
}
