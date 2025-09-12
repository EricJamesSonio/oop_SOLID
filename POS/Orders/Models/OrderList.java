package POS.Orders.Models;
//import POS.Orders.Models.OrderItem;
import POS.Menu.Base.MenuItem;
import java.util.*;


public class OrderList {
    private HashMap<OrderItem , Integer> items;

    public OrderList () {
        this.items = new HashMap<>();
    }

    public void addItem(MenuItem item , int quantity) {
        OrderItem orderItem = new OrderItem(item);
        OrderItem existing = findItem(item.getCode());

        if (existing != null) {
            int newqtty = items.get(existing) + quantity;
            items.put(existing, newqtty);
        } else {
            items.put(orderItem, quantity);
        }
    } 

    public void removeItem(int code) {
        OrderItem existing = findItem(code);

        if (existing != null) {
            items.remove(existing);
        } else {
            return;
        }
    }

    public void updateQuantity(int code, int quantity) {
        OrderItem existing = findItem(code);

        if (existing != null) {
            items.put(existing, quantity); // Replace the Old quantity by the new Quantity
        }
    }

    public void displayItems() {
        for (OrderItem item : items.keySet()) {
            System.out.println(item.getDetails());
        }
    }

    public OrderItem findItem(int code) {
        for (OrderItem item : items.keySet()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }

    public HashMap <OrderItem, Integer> getItems() {
        return items;
    }
}
