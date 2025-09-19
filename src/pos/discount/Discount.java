package pos.discount;

import pos.model.Order;

public interface Discount {
    double apply(Order order);
    String getLabel();
    double getRate(); // expose percentage (e.g. 0.12 for 12%)
}
