
package pos.ui.tui;

import pos.ui.UI;
import pos.service.*;
import pos.model.Employee;
import java.util.Scanner;

public class MainTUI implements UI {
    private AuthService auth;
    private EmployeeService empService;
    private InventoryService inventory;
    private MenuService menu;
    private OrderService orders;
    private TableService tables;
    private Scanner scanner = new Scanner(System.in);

    public static MainTUI create(){
        InventoryService inv = new InventoryService();
        MenuService menu = new MenuService(inv);
        EmployeeService es = new EmployeeService();
        AuthService auth = new AuthService();
        TableService ts = new TableService();
        OrderService os = new OrderService(menu, inv);
        MainTUI tui = new MainTUI();
        tui.auth = auth;
        tui.empService = es;
        tui.inventory = inv;
        tui.menu = menu;
        tui.orders = os;
        tui.tables = ts;
        return tui;
    }

    @Override
    public void start(){
        System.out.println("Welcome to Simple RESTO POS (TUI)"); 
        int attempts = 0;
        while(attempts < 3){
            System.out.print("Username: "); String u = scanner.nextLine().trim();
            System.out.print("Password: "); String p = scanner.nextLine().trim();
            int empId = auth.login(u,p);
            if (empId > 0){
                Employee e = empService.findById(empId);
                System.out.println("Logged in as: " + e);
                if (auth.roleOf(u)!=null && auth.roleOf(u).equals("Admin")){
                    pos.ui.tui.AdminDashboardTUI admin = new AdminDashboardTUI(e, menu, empService, auth, tables, orders, inventory);
                    admin.start();
                } else {
                    pos.ui.tui.CashierDashboardTUI cash = new CashierDashboardTUI(e, menu, empService, auth, tables, orders, inventory);
                    cash.start();
                }
                return;
            } else {
                attempts++;
                System.out.println("Invalid credentials ("+attempts+"/3)"); 
            }
        }
        System.out.println("Too many failed attempts. Exiting."); 
    }
}
