package pos.ui.tui;

import pos.ui.UI;
import pos.model.Employee;
import pos.model.MenuItem;
import pos.model.Order;
import pos.model.OrderItem;
import pos.discount.Discount;
import pos.service.MenuService;
import pos.service.EmployeeService;
import pos.service.AuthService;
import pos.service.TableService;
import pos.service.OrderService;
import pos.service.InventoryService;
import pos.service.FileUtil;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Scanner;
import pos.AppConfig;


public class CashierDashboardTUI implements UI {
    private Employee self;
    private MenuService menu;
    private EmployeeService empService;
    private AuthService auth;
    private TableService tableService;
    private OrderService orderService;
    private InventoryService inventory;
    private Scanner scanner = new Scanner(System.in);

    public CashierDashboardTUI(Employee self, MenuService menu, EmployeeService empService, 
                              AuthService auth, TableService tableService, OrderService orderService, 
                              InventoryService inventory) {
        this.self = self; 
        this.menu = menu; 
        this.empService = empService; 
        this.auth = auth; 
        this.tableService = tableService; 
        this.orderService = orderService; 
        this.inventory = inventory;
    }

    @Override 
    public void start() {
        while(true) {
            System.out.println("\\n--- CASHIER DASHBOARD ---");
            System.out.println("1) View Menu");
            System.out.println("2) Create Order");
            System.out.println("3) View Orders");
            System.out.println("4) Tables");
            System.out.println("5) Checkout Order");
            System.out.println("0) Logout");
            System.out.print("Choose: "); 
            String c = scanner.nextLine().trim();
            
            if (c.equals("1")) viewMenu();
            else if (c.equals("2")) createOrder();
            else if (c.equals("3")) viewOrders();
            else if (c.equals("4")) viewTables();
            else if (c.equals("5")) checkoutOrder();
            else if (c.equals("0")) { System.out.println("Logging out..."); break; }
            else System.out.println("Invalid"); 
        }
    }

    private void viewMenu() {
        System.out.println("\\n--- MENU ITEMS ---");
        System.out.println("ID | Name | Price | Category | Availability");
        for (MenuItem m : menu.all()) {
            String avail = "AVAILABLE";
            // check availability by trying to see if ingredients or drink stock suffice for qty 1
            boolean ok = true;
            if (m.getRecipe() == null || m.getRecipe().getItems().isEmpty()) {
                // drink - check ingredient with same name
                pos.model.Ingredient ingFound = null;
                for (pos.model.Ingredient i : inventory.all()) 
                    if (i.getName().toLowerCase().contains(m.getName().toLowerCase())) { 
                        ingFound = i; break; 
                    }
                if (ingFound==null || ingFound.getStock() < 1) ok = false;
            } else {
                for (Map.Entry<Integer,Integer> e : m.getRecipe().getItems().entrySet()) {
                    pos.model.Ingredient ing = inventory.findById(e.getKey());
                    if (ing==null || ing.getStock() < e.getValue()) { ok = false; break; }
                }
            }
            if (!ok) avail = "NOT AVAILABLE";
            System.out.println(m.getId() + " | " + m.getName() + " | " + String.format("%.2f", m.getPrice()) + 
                             " | " + m.getCategory() + " | " + avail);
        }
    }

    public void createOrder() {
        try {
            System.out.println("Is this walk-in? (y/n) "); 
            String w = scanner.nextLine().trim();
            int tableId = 0;
            boolean seniorOrPwd = false;
            
            if (w.equalsIgnoreCase("n")) {
                // choose table
                for (pos.model.Table t : tableService.all()) System.out.println(t.toString());
                System.out.print("Select table id: "); 
                tableId = Integer.parseInt(scanner.nextLine().trim());
                pos.model.Table t = tableService.findById(tableId);
                if (t==null) { System.out.println("Table not found"); return; }
                
                // allow ordering for OCCUPIED (existing customer) or AVAILABLE (assign customer)
                if (t.getStatus().equals("DIRTY")) {
                    System.out.println("Table is dirty and cannot take orders until cleaned."); 
                    return;
                }
                if (t.getStatus().equals("AVAILABLE")) {
                    System.out.print("Number of people: "); 
                    int pc = Integer.parseInt(scanner.nextLine().trim());
                    if (pc > t.getCapacity()) { System.out.println("Exceeds capacity"); return; }
                    // assign as occupied for this order session
                    t.setStatus("OCCUPIED"); 
                    t.setCustomerCount(pc); 
                    tableService.saveAll();
                } else {
                    // if OCCUPIED we proceed - existing customer; do not reassign count
                    System.out.println("Selected table is currently occupied. Proceeding to take order for existing customer."); 
                }
            } else {
                System.out.print("Is customer senior or PWD? (y/n): "); 
                String s = scanner.nextLine().trim();
                seniorOrPwd = s.equalsIgnoreCase("y");
            }
            
            Order o = new Order(orderService.nextId(), self.getId(), tableId);
            o.setSeniorOrPwd(seniorOrPwd);
            
            while(true) {
                viewMenu();
                System.out.print("Enter menu id to add (0 to finish): "); 
                int mid = Integer.parseInt(scanner.nextLine().trim());
                if (mid==0) break;
                
                MenuItem mi = menu.findById(mid);
                if (mi==null) { System.out.println("No such item"); continue; }
                
                System.out.print("Quantity: "); 
                int q = Integer.parseInt(scanner.nextLine().trim());
                o.addItem(new OrderItem(mi.getId(), mi.getName(), q, mi.getPrice()));
            }
            
            // ask for discount (applies to both walk-in and table)
            System.out.println("Apply discount?");
            System.out.println("1) None");
            System.out.println("2) " + AppConfig.DISCOUNT_PWD.getLabel());
            System.out.println("3) " + AppConfig.DISCOUNT_SENIOR.getLabel());
            System.out.println("4) " + AppConfig.DISCOUNT_PWD_SENIOR.getLabel());

            System.out.print("Choice: ");
            String dch = scanner.nextLine().trim();
            Discount selected = null;
            if (dch.equals("2")) {
                selected = AppConfig.DISCOUNT_PWD;
            } else if (dch.equals("3")) {
                selected = AppConfig.DISCOUNT_SENIOR;
            } else if (dch.equals("4")) {
                selected = AppConfig.DISCOUNT_PWD_SENIOR;
            }

            // delegate discount application to OrderService (keeps UI pure)
            orderService.applyDiscount(o, selected);
            // ensure seniorOrPwd flag matches whether discount was applied (for legacy uses)
            o.setSeniorOrPwd(!o.getDiscountType().equals("NONE"));
    
            // try deduct ingredients
            List<String> probs = orderService.tryDeductIngredients(o);
            if (!probs.isEmpty()) {
                System.out.println("Cannot create order due to: "); 
                for (String p: probs) System.out.println(" - " + p);
                return;
            }
            
            // save order (pending for table, immediate checkout for walk-in)
            orderService.add(o);
            System.out.println("Order created with id="+o.getId());
            if (o.getTableId() == 0) {
                // walk-in -> checkout immediately
                checkout(o.getId());
            }
        } catch(Exception ex) { 
            System.out.println("Failed: " + ex.getMessage()); 
        }
    }

    private void viewOrders() {
        System.out.println("\\n--- ORDERS ---");
        for (Order o : orderService.all()) {
            // filter: cashier only sees own orders
            if (!self.getRole().equals("Admin") && o.getCreatedBy() != self.getId()) continue;
            System.out.println("Order#"+o.getId()+" | status:"+o.getStatus()+" | table:"+o.getTableId()+
                " | subtotal:"+String.format("%.2f", o.getSubtotal())+
                " | discount:"+String.format("%.2f", o.getDiscountAmount()));
        }
        System.out.print("Select order id to view or 0: "); 
        int id = Integer.parseInt(scanner.nextLine().trim());
        if (id==0) return;
        
        Order o = orderService.findById(id);
        if (o==null) { System.out.println("Not found"); return; }
        
        System.out.println("Items:"); 
        for (OrderItem it : o.getItems()) 
            System.out.println(" - " + it.getMenuItemName() + " x" + it.getQuantity() + 
                " -> " + String.format("%.2f", it.getSubtotal()));
        System.out.println("Subtotal: " + String.format("%.2f", o.getSubtotal()));
        System.out.println("Discount: " + String.format("%.2f", o.getDiscountAmount()));
        System.out.println("Total: " + String.format("%.2f", o.getTotal()));
        System.out.println("1) Checkout  2) Refund  3) Delete  0) Back");
        String c = scanner.nextLine().trim();
        if (c.equals("1")) checkout(o.getId());
        else if (c.equals("2")) refund(o.getId());
        else if (c.equals("3")) delete(o.getId());
    }

    private void checkoutOrder() {
        System.out.println("\\n--- ORDERS ---");
        for (Order o : orderService.all()) {
            // filter: cashier only sees own orders
            if (!self.getRole().equals("Admin") && o.getCreatedBy() != self.getId()) continue;
            System.out.println("Order#"+o.getId()+" | status:"+o.getStatus()+" | table:"+o.getTableId()+
                " | subtotal:"+String.format("%.2f", o.getSubtotal())+
                " | discount:"+String.format("%.2f", o.getDiscountAmount()));
        }
        System.out.print("Enter order id to checkout (0 to cancel): "); 
        int id = Integer.parseInt(scanner.nextLine().trim());
        if (id==0) return;
        checkout(id);
    }

    public void checkout(int orderId) {
        Order o = orderService.findById(orderId);
        if (o==null) { System.out.println("Not found"); return; }
        if (o.getStatus().equals("COMPLETED")) { 
            System.out.println("Already completed"); 
            return; 
        }
        
        System.out.println("Checkout Order#"+o.getId()+" total:"+String.format("%.2f", o.getTotal()));
        System.out.print("Enter payment amount: "); 
        double pay = Double.parseDouble(scanner.nextLine().trim());
        if (pay < o.getTotal()) { System.out.println("Not enough payment"); return; }
        
        double change = pay - o.getTotal();
        System.out.println("Change: " + String.format("%.2f", change));
        
        o.setStatus("COMPLETED");
        orderService.update(o);
        
        // mark table dirty if applicable
        if (o.getTableId() != 0) {
            pos.model.Table t = tableService.findById(o.getTableId());
            if (t!=null) { t.setStatus("DIRTY"); tableService.saveAll(); }
        }
        
        // save receipt to sales.txt (simple)
        String rec = o.getId() + "|" + o.getCreatedBy() + "|" + String.format("%.2f", o.getSubtotal()) + 
                    "|" + o.getDiscountType() + "|" + String.format("%.2f", o.getDiscountAmount()) + 
                    "|" + String.format("%.2f", o.getTotal()) + "|" + o.getCreatedAtString();
        FileUtil.appendLine("resources/data/sales.txt", rec);
        System.out.println("Checkout complete and receipt saved."); 
    }

    public void refund(int orderId) {
        Order o = orderService.findById(orderId);
        if (o==null) { System.out.println("Not found"); return; }
        if (o.getStatus().equals("REFUNDED")) { 
            System.out.println("Already refunded"); 
            return; 
        }
        
        System.out.print("Are you sure you want to refund this order? (y/n): "); 
        String conf = scanner.nextLine().trim();
        if (!conf.equalsIgnoreCase("y")) { System.out.println("Cancelled."); return; }
        
        o.setStatus("REFUNDED");
        orderService.update(o);
        
        // append refund entry to sales.txt (negative total and REFUND tag)
        String recRefund = o.getId() + "|" + o.getCreatedBy() + "|" + String.format("%.2f", o.getSubtotal()) + 
                          "|" + o.getDiscountType() + "|" + String.format("%.2f", o.getDiscountAmount()) + 
                          "|" + String.format("%.2f", -o.getTotal()) + "|" + o.getCreatedAtString() + "|REFUND";
        FileUtil.appendLine("resources/data/sales.txt", recRefund);
        System.out.println("Order marked as REFUNDED.");
    }

    public void delete(int orderId) {
        List<String> lines = pos.service.FileUtil.readAllLines("resources/data/orders.txt");
        List<String> out = new ArrayList<String>();
        for (String l : lines) {
            String[] p = l.split("\\\\|", -1);
            try { 
                int id = Integer.parseInt(p[0]); 
                if (id!=orderId) out.add(l); 
            } catch(Exception ex) { 
                out.add(l); 
            }
        }
        pos.service.FileUtil.writeAll("resources/data/orders.txt", out);
        orderService.load();
        System.out.println("Order removed."); 
    }

    private void viewTables() {
        System.out.println("\\n--- TABLES ---");
        for (pos.model.Table t : tableService.all()) System.out.println(t.toString());
        System.out.print("1) Assign Customer 2) Clean Table 0) Back: "); 
        String c = scanner.nextLine().trim();
        
        if (c.equals("1")) {
            System.out.print("Table id: "); 
            int id = Integer.parseInt(scanner.nextLine().trim());
            pos.model.Table t = tableService.findById(id);
            if (t==null) { System.out.println("Not found"); return; }
            if (!t.getStatus().equals("AVAILABLE")) { 
                System.out.println("Table not available"); 
                return; 
            }
            System.out.print("Number people: "); 
            int pc = Integer.parseInt(scanner.nextLine().trim());
            if (pc > t.getCapacity()) { System.out.println("Exceeds capacity"); return; }
            t.setStatus("OCCUPIED"); 
            t.setCustomerCount(pc); 
            tableService.saveAll(); 
            System.out.println("Assigned."); 
        } else if (c.equals("2")) {
            System.out.print("Table id to clean: "); 
            int id = Integer.parseInt(scanner.nextLine().trim());
            pos.model.Table t = tableService.findById(id);
            if (t==null) { System.out.println("Not found"); return; }
            if (!t.getStatus().equals("DIRTY")) { System.out.println("Not dirty"); return; }
            t.setStatus("AVAILABLE"); 
            t.setCustomerCount(0); 
            tableService.saveAll(); 
            System.out.println("Cleaned."); 
        }
    }
}