package POS.Orders.Management.Interfaces;

import POS.Orders.Models.Order;
import java.util.List;

public interface IOrderViewer {
    void displayOrders(List<Order> orders);
}
