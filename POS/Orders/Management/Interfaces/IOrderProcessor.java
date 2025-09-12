package POS.Orders.Management.Interfaces;

import POS.Orders.Models.Order;
import POS.Orders.Base.OrderStatus;

public interface IOrderProcessor {
    boolean processOrder(Order order, double payment);
    boolean updateStatus(Order order, OrderStatus newStatus);
}
