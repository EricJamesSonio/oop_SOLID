package POS.Orders.UI.TUI;

import POS.Common.ConsoleHelper;
import POS.Orders.Management.OrderManagementFacade;
import POS.Menu.Base.MenuItem;
import POS.Menu.Services.MenuService;
import POS.Orders.Base.CustomerType;
import POS.Orders.Base.OrderStatus;
import POS.Orders.Models.OrderList;
import POS.Orders.Services.DiscountCalculator;

public class OrderManagementTUI {
    private final OrderManagementFacade facade;
    private final MenuService menuService;
    private final ConsoleHelper console;

    public OrderManagementTUI(OrderManagementFacade facade, MenuService menuService, ConsoleHelper console) {
        this.facade = facade;
        this.menuService = menuService;
        this.console = console;
    }

    public void showMenu() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("\n--- Order Management ---");
            System.out.println("1. Add Order");
            System.out.println("2. Process Order");
            System.out.println("3. Update Order Status");
            System.out.println("4. Refund / Return Order");
            System.out.println("5. Display All Orders");
            System.out.println("0. Exit");

            choice = console.readMenuChoice("Choose: ", 0, 5);

            switch (choice) {
                case 1 -> addOrder();
                case 2 -> processOrder();
                case 3 -> updateOrderStatus();
                case 4 -> refundOrder();
                case 5 -> facade.displayOrders();
                case 0 -> System.out.println("Exiting Order Management TUI...");
            }
        }
    }

    private void addOrder() {
        CustomerType type = selectCustomerType();
        OrderList orderList = buildOrderList();

        DiscountCalculator discountCalculator = POS.Orders.Services.DiscountFactory.createDiscountCalculator(type);

        boolean success = facade.addOrder(type, orderList, discountCalculator);
        System.out.println(success ? "Order added successfully." : "Failed to add order.");
    }

    private void processOrder() {
        int id = console.readInt("Enter Order ID: ");
        double payment = console.readDouble("Enter payment amount: ");
        boolean success = facade.processOrder(id, payment);
        System.out.println(success ? "Order processed successfully." : "Failed to process order.");
    }

    private void updateOrderStatus() {
        int id = console.readInt("Enter Order ID: ");
        OrderStatus status = selectOrderStatus();
        boolean success = facade.updateOrderStatus(id, status);
        System.out.println(success ? "Order status updated." : "Failed to update status.");
    }

    private void refundOrder() {
        int id = console.readInt("Enter Order ID: ");
        System.out.println("1. Full Refund");
        System.out.println("2. Partial Refund");
        System.out.println("3. Return Order");

        int choice = console.readMenuChoice("Select option: ", 1, 3);

        switch (choice) {
            case 1 -> facade.fullRefund(id);
            case 2 -> {
                double amount = console.readDouble("Enter refund amount: ");
                facade.partialRefund(id, amount);
            }
            case 3 -> facade.returnOrder(id);
        }
    }

    // --- Helpers ---
    private CustomerType selectCustomerType() {
        System.out.println("Select Customer Type:");
        System.out.println("1. Regular");
        System.out.println("2. PWD");
        System.out.println("3. Senior");

        int choice = console.readMenuChoice("Enter choice: ", 1, 3);
        return switch (choice) {
            case 2 -> new CustomerType(true, false);   // PWD
            case 3 -> new CustomerType(false, true);   // Senior
            default -> new CustomerType(false, false); // Regular
        };
    }

    private OrderStatus selectOrderStatus() {
        System.out.println("Select Status:");
        for (OrderStatus s : OrderStatus.values()) {
            System.out.println((s.ordinal() + 1) + ". " + s);
        }
        int choice = console.readMenuChoice("Enter choice: ", 1, OrderStatus.values().length);
        return OrderStatus.values()[choice - 1];
    }

    private OrderList buildOrderList() {
        OrderList list = new OrderList();
        boolean adding = true;

        while (adding) {
            menuService.showMenu();
            int code = console.readInt("Enter Menu Code (0 to finish): ");
            if (code == 0) break;

            MenuItem menuItem = menuService.getMenuItemByCode(code);
            if (menuItem == null) {
                System.out.println("Invalid code, try again.");
                continue;
            }

            int qty = console.readInt("Enter quantity: ");
            list.addItem(menuItem, qty);
        }

        return list;
    }
}
