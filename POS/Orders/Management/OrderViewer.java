package POS.Orders.Management;

import POS.Orders.Management.Interfaces.IOrderViewer;
import POS.Orders.Models.Order;
import java.util.List;

public class OrderViewer implements IOrderViewer {

    @Override
    public void displayOrders(List<Order> orders) {
        for (Order o : orders) {
            System.out.println(o);
        }
    }
}
