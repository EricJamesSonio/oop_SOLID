package pos.ui.tui;

import pos.ui.UI;
import pos.model.Employee;
import pos.service.MenuService;
import pos.service.EmployeeService;
import pos.service.AuthService;
import pos.service.TableService;
import pos.service.OrderService;
import pos.service.InventoryService;

import java.util.Scanner;

public class AdminDashboardTUI implements UI {
    private Employee self;
    private MenuService menu;
    private EmployeeService empService;
    private AuthService auth;
    private TableService tableService;
    private OrderService orderService;
    private InventoryService inventory;
    private Scanner scanner = new Scanner(System.in);

    public AdminDashboardTUI(Employee self, MenuService menu, EmployeeService empService,
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
        while (true) {
            System.out.println("\n--- ADMIN DASHBOARD ---");
            System.out.println("1) Menu Management");
            System.out.println("2) Order Management");
            System.out.println("3) Table Management");
            System.out.println("4) Employee Management");
            System.out.println("5) Sales Report");
            System.out.println("6) Inventory Management");
            System.out.println("0) Logout");
            System.out.print("Choose: ");
            String c = scanner.nextLine().trim();

            if (c.equals("1")) openMenuManagement();
            else if (c.equals("2")) openOrderManagement();
            else if (c.equals("3")) openTableManagement();
            else if (c.equals("4")) openEmployeeManagement();
            else if (c.equals("5")) openSalesReportManagement();
            else if (c.equals("6")) openInventoryManagement();
            else if (c.equals("0")) {
                System.out.println("Logging out...");
                break;
            } else {
                System.out.println("Invalid");
            }
        }
    }

    private void openMenuManagement() {
        MenuManagementTUI menuTUI = new MenuManagementTUI(menu, inventory);
        menuTUI.start();
    }

    private void openOrderManagement() {
        OrderManagementTUI orderTUI = new OrderManagementTUI(
            self, menu, empService, auth, tableService, orderService, inventory
        );
        orderTUI.start();
    }

    private void openTableManagement() {
        TableManagementTUI tableTUI = new TableManagementTUI(tableService);
        tableTUI.start();
    }

    private void openEmployeeManagement() {
        EmployeeManagementTUI employeeTUI = new EmployeeManagementTUI(empService, auth);
        employeeTUI.start();
    }

    private void openSalesReportManagement() {
        SalesReportManagementTUI salesTUI = new SalesReportManagementTUI();
        salesTUI.start();
    }

    private void openInventoryManagement() {
        InventoryManagementTUI inventoryTUI = new InventoryManagementTUI(inventory);
        inventoryTUI.start();
    }
}
