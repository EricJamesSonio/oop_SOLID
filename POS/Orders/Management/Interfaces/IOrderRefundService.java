package POS.Orders.Management.Interfaces;

import POS.Orders.Models.Order;

public interface IOrderRefundService {
    void fullRefund(Order order);
    void partialRefund(Order order, double amount);
    void returnOrder(Order order);
}
