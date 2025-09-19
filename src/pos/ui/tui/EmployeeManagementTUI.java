package pos.ui.tui;

import pos.ui.UI;
import pos.model.Employee;
import pos.model.Admin;
import pos.model.Cashier;
import pos.service.EmployeeService;
import pos.service.AuthService;

import java.util.Scanner;

public class EmployeeManagementTUI implements UI {
    private EmployeeService empService;
    private AuthService auth;
    private Scanner scanner = new Scanner(System.in);

    public EmployeeManagementTUI(EmployeeService empService, AuthService auth) {
        this.empService = empService;
        this.auth = auth;
    }

    @Override
    public void start() {
        while (true) {
            System.out.println("\n--- EMPLOYEE MANAGEMENT ---");
            System.out.println("1) View Employees");
            System.out.println("2) Add Employee");
            System.out.println("3) Remove Employee");
            System.out.println("4) Assign Auth to Employee");
            System.out.println("0) Back");
            System.out.print("Choose: ");
            String c = scanner.nextLine().trim();

            if (c.equals("1")) viewEmployees();
            else if (c.equals("2")) addEmployee();
            else if (c.equals("3")) removeEmployee();
            else if (c.equals("4")) assignAuth();
            else if (c.equals("0")) return;
            else System.out.println("Invalid");
        }
    }

    private void viewEmployees() {
        System.out.println("\n--- EMPLOYEES ---");
        for (Employee e : empService.all()) {
            System.out.println(e.toString());
        }
    }

    private void addEmployee() {
        int id = empService.nextId();
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Role (Admin/Cashier): ");
        String role = scanner.nextLine().trim();

        if (role.equalsIgnoreCase("Admin")) {
            empService.add(new Admin(id, name), "Admin");
        } else {
            empService.add(new Cashier(id, name), "Cashier");
        }

        System.out.println("Added employee id=" + id);
    }

    private void removeEmployee() {
        viewEmployees();
        System.out.print("Enter employee id to remove: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        empService.remove(id);
        System.out.println("Removed if existed.");
    }

    private void assignAuth() {
        viewEmployees();
        System.out.print("Employee id: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        Employee e = empService.findById(id);
        if (e == null) {
            System.out.println("Not found");
            return;
        }

        System.out.print("Username: ");
        String u = scanner.nextLine().trim();
        System.out.print("Password: ");
        String p = scanner.nextLine().trim();

        auth.addAuth(u, p, id, e.getRole());
        System.out.println("Auth assigned.");
    }
}
