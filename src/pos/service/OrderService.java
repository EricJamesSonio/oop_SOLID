package pos.service;

import pos.model.Order;
import pos.model.OrderItem;
import pos.model.MenuItem;
import pos.discount.*;
import java.util.*;

/**
 * Refactored OrderService: focuses on orchestration of orders.
 * - Delegates discount application to DiscountManager
 * - Delegates inventory deduction/checks to InventoryManager
 * - Keeps persistence using FileUtil to maintain compatibility
 */
public class OrderService {
    private String path = "resources/data/orders.txt";
    private List<Order> orders = new ArrayList<Order>();
    private MenuService menuService;
    private InventoryService inventory;
    private DiscountManager discountManager;
    private InventoryManager inventoryManager;

    public OrderService(MenuService menuService, InventoryService inventory){
        this.menuService = menuService;
        this.inventory = inventory;
        this.discountManager = new DiscountManager();
        this.inventoryManager = new InventoryManager(inventory, menuService);
        load();
    }

    public void load(){
        orders.clear();
        List<String> lines = FileUtil.readAllLines(path);
        for (String l : lines){
            Order o = Order.parse(l);
            if (o!=null) orders.add(o);
        }
    }

    public List<Order> all(){ return orders; }

    public Order findById(int id){
        for (Order o: orders) if (o.getId()==id) return o;
        return null;
    }

    public int nextId(){
        int max = 0;
        for (Order o: orders) if (o.getId()>max) max = o.getId();
        return max+1;
    }

    public void add(Order o){
        FileUtil.appendLine(path, serialize(o));
        load();
    }

    public void update(Order o){
        List<String> lines = FileUtil.readAllLines(path);
        List<String> out = new ArrayList<String>();
        for (String l : lines){
            try{
                String[] p = l.split("\\|", -1);
                int id = Integer.parseInt(p[0]);
                if (id == o.getId()){
                    out.add(serialize(o));
                } else out.add(l);
            }catch(Exception ex){ out.add(l); }
        }
        FileUtil.writeAll(path, out);
        load();
    }

    private String serialize(Order o){
        // id|createdBy|tableId|status|subtotal|discountAmount|total|isPaid|createdAt|discountType|items
        String items = o.serializeItems();
        String subtotal = String.format("%.2f", o.getSubtotal());
        String discount = String.format("%.2f", o.getDiscountAmount());
        String total = String.format("%.2f", o.getTotal());
        String paid = o.isPaid() ? "true" : "false";
        String created = o.getCreatedAtString();
        String dtype = o.getDiscountType();
        return o.getId() + "|" + o.getCreatedBy() + "|" + o.getTableId() + "|" + o.getStatus()
            + "|" + subtotal + "|" + discount + "|" + total + "|" + paid + "|" + created + "|" + dtype + "|" + items;
    }

    public void applyDiscount(Order o, pos.discount.Discount d){
        // delegate to DiscountManager
        this.discountManager.applyDiscount(o, d);
    }

    public List<String> tryDeductIngredients(Order o){
        return this.inventoryManager.tryDeductIngredients(o);
    }
}