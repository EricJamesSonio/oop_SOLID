package POS.Orders.Management;

import POS.Orders.Management.Interfaces.IOrderProcessor;
import POS.Orders.Base.OrderStatus;
import POS.Orders.Models.Order;

public class OrderProcessor implements IOrderProcessor {

    @Override
    public boolean processOrder(Order order, double payment) {
        if (order == null || payment < order.getTotalPayable()) return false;
        order.setStatus(OrderStatus.COMPLETED);
        return true;
    }

    @Override
    public boolean updateStatus(Order order, OrderStatus newStatus) {
        if (order == null) return false;
        order.setStatus(newStatus);
        return true;
    }
}
