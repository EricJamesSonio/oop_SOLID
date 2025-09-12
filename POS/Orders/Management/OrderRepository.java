package POS.Orders.Management;

import POS.Orders.Management.Interfaces.IOrderRepository;
import POS.Orders.Models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements IOrderRepository {
    private final List<Order> orders = new ArrayList<>();

    @Override
    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public boolean removeOrder(int id) {
        Order order = findOrder(id);
        if (order != null) {
            orders.remove(order);
            return true;
        }
        return false;
    }

    @Override
    public Order findOrder(int id) {
        return orders.stream().filter(o -> o.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Order> getOrders() {
        return List.copyOf(orders);
    }
}
