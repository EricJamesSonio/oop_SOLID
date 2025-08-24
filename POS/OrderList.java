package POS;
import java.util.*;


public class OrderList {
    private HashMap<MenuItem , Integer> items;

    public OrderList () {
        this.items = new HashMap<>();
    }

    public void addItem(MenuItem item , int quantity) {
        MenuItem existing = findItem(item.getCode());

        if (existing != null) {
            int newqtty = items.get(existing) + quantity;
            items.put(existing, newqtty);
        } else {
            items.put(item, quantity);
        }
    } 

    public void removeItem(int code) {
        MenuItem existing = findItem(code);

        if (existing != null) {
            items.remove(existing);
        } else {
            return;
        }
    }

    public void updateQuantity(int code, int quantity) {
        MenuItem existing = findItem(code);

        if (existing != null) {
            items.put(existing, quantity); // Replace the Old quantity by the new Quantity
        }
    }

    public void displayItems() {
        for (MenuItem item : items.keySet()) {
            System.out.println(item.getDetails());
        }
    }

    public MenuItem findItem(int code) {
        for (MenuItem item : items.keySet()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }

    public HashMap <MenuItem, Integer> getItems() {
        return items;
    }
}
