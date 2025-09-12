package POS.Orders.Management.Interfaces;

import POS.Orders.Models.Order;
import java.util.List;

public interface IOrderRepository {
    void addOrder(Order order);
    boolean removeOrder(int id);
    Order findOrder(int id);
    List<Order> getOrders();
}
