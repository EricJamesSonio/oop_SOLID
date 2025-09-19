package pos.ui.tui;

import pos.ui.UI;
import pos.model.Employee;
import pos.model.Order;
import pos.model.OrderItem;
import pos.service.AuthService;
import pos.service.EmployeeService;
import pos.service.InventoryService;
import pos.service.MenuService;
import pos.service.OrderService;
import pos.service.TableService;

import java.util.List;
import java.util.Scanner;


public class OrderManagementTUI implements UI {
    private Employee currentUser;
    private MenuService menu;
    private EmployeeService empService;
    private AuthService auth;
    private TableService tableService;
    private OrderService orderService;
    private InventoryService inventory;
    private Scanner scanner = new Scanner(System.in);

    public OrderManagementTUI(Employee currentUser, MenuService menu, EmployeeService empService, 
                             AuthService auth, TableService tableService, OrderService orderService, 
                             InventoryService inventory) {
        this.currentUser = currentUser;
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
            System.out.println("\n--- ORDER MANAGEMENT ---");
            System.out.println("1) View Orders");
            System.out.println("2) Create Order");
            System.out.println("3) Checkout Order");
            System.out.println("4) Refund Order");
            System.out.println("5) Delete Order");
            System.out.println("0) Back");
            System.out.print("Choose: "); 
            String c = scanner.nextLine().trim();
            
            if (c.equals("1")) viewOrders();
            else if (c.equals("2")) createOrder();
            else if (c.equals("3")) checkoutOrder();
            else if (c.equals("4")) refundOrder();
            else if (c.equals("5")) deleteOrder();
            else if (c.equals("0")) return;
            else System.out.println("Invalid");
        }
    }

    private void viewOrders() {
        System.out.println("\n--- ALL ORDERS ---");
        for (Order o : orderService.all()) {
            System.out.println("Order#"+o.getId()+" | status:"+o.getStatus()+" | table:"+o.getTableId()+
                " | subtotal:"+String.format("%.2f", o.getSubtotal())+
                " | discount:"+String.format("%.2f", o.getDiscountAmount())+
                " | total:"+String.format("%.2f", o.getTotal())+
                " | createdBy:"+o.getCreatedBy());
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
    }

    private void createOrder() {
        CashierDashboardTUI cashierTui = new CashierDashboardTUI(currentUser, menu, empService, 
            auth, tableService, orderService, inventory);
        cashierTui.createOrder();
    }

    private void checkoutOrder() {
        List<Order> orders = orderService.all();
        if (orders.isEmpty()) {
            System.out.println("No orders available to checkout.");
            return;
        }

        System.out.println("\n--- ALL ORDERS ---");
        for (Order o : orders) {
            System.out.println("Order#" + o.getId() 
                + " | status:" + o.getStatus() 
                + " | table:" + o.getTableId() 
                + " | subtotal:" + String.format("%.2f", o.getSubtotal()) 
                + " | discount:" + String.format("%.2f", o.getDiscountAmount()) 
                + " | createdBy:" + o.getCreatedBy());
        }

        System.out.print("Enter order id to checkout (0 to cancel): "); 
        int id = Integer.parseInt(scanner.nextLine().trim());
        if (id == 0) return;

        CashierDashboardTUI cashierTui = new CashierDashboardTUI(currentUser, menu, empService, 
            auth, tableService, orderService, inventory);
        cashierTui.checkout(id);
    }

    private void refundOrder() {
        List<Order> orders = orderService.all();
        if (orders.isEmpty()) {
            System.out.println("No orders available to refund.");
            return;
        }

        System.out.println("\n--- ALL ORDERS ---");
        for (Order o : orders) {
            System.out.println("Order#" + o.getId()
                + " | status:" + o.getStatus()
                + " | table:" + o.getTableId()
                + " | subtotal:" + String.format("%.2f", o.getSubtotal())
                + " | discount:" + String.format("%.2f", o.getDiscountAmount())
                + " | createdBy:" + o.getCreatedBy());
        }

        System.out.print("Enter order id to refund (0 to cancel): ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        if (id == 0) return;

        Order order = orderService.findById(id);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }

        CashierDashboardTUI cashierTui = new CashierDashboardTUI(currentUser, menu, empService, 
            auth, tableService, orderService, inventory);
        cashierTui.refund(id);
    }

    private void deleteOrder() {
        List<Order> orders = orderService.all();
        if (orders.isEmpty()) {
            System.out.println("No orders available to delete.");
            return;
        }

        System.out.println("\n--- ALL ORDERS ---");
        for (Order o : orders) {
            System.out.println("Order#" + o.getId()
                + " | status:" + o.getStatus()
                + " | table:" + o.getTableId()
                + " | subtotal:" + String.format("%.2f", o.getSubtotal())
                + " | discount:" + String.format("%.2f", o.getDiscountAmount())
                + " | createdBy:" + o.getCreatedBy());
        }

        System.out.print("Enter order id to delete (0 to cancel): ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        if (id == 0) return;

        Order o = orderService.findById(id);
        if (o == null) {
            System.out.println("Order not found.");
            return;
        }

        System.out.print("Are you sure you want to delete Order#" + id + "? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (!confirm.equals("y")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        CashierDashboardTUI cashierTui = new CashierDashboardTUI(currentUser, menu, empService, 
            auth, tableService, orderService, inventory);
        cashierTui.delete(id);
    }
}