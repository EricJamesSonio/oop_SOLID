package POS.Employee.Management.UI.TUI;

import POS.Common.ConsoleHelper;
import POS.Employee.Base.Employee;
import POS.Employee.Login.Auth;
import POS.Employee.Management.Interfaces.IAccountService;
import POS.Employee.Management.Interfaces.IAccountDisplay;
import POS.Employee.Management.Interfaces.IEmployeeService;
import POS.Employee.Staff.Admin;
import POS.Employee.Staff.Cashier;
import POS.Employee.Management.Interfaces.IEmployeeDisplay;

import java.util.List;

public class EmployeeManagementTUI {
    private final IEmployeeService employeeService;
    private final IAccountService accountService;
    private final IEmployeeDisplay employeeDisplay;
    private final IAccountDisplay accountDisplay;
    private final ConsoleHelper console;

    public EmployeeManagementTUI(IEmployeeService employeeService,
                                 IAccountService accountService,
                                 IEmployeeDisplay employeeDisplay,
                                 IAccountDisplay accountDisplay,
                                 ConsoleHelper console) {
        this.employeeService = employeeService;
        this.accountService = accountService;
        this.employeeDisplay = employeeDisplay;
        this.accountDisplay = accountDisplay;
        this.console = console;
    }

    public void showMenu() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("\n--- Employee Management ---");
            System.out.println("1. Display Employees");
            System.out.println("2. Add Employee");
            System.out.println("3. Update Employee");
            System.out.println("4. Register Employee (Auth)");
            System.out.println("5. Remove Employee");
            System.out.println("0. Exit");

            choice = console.readMenuChoice("Choose: ", 0, 5);

            switch (choice) {
                case 1 -> displayEmployees();
                case 2 -> addEmployee();
                case 3 -> updateEmployee();
                case 4 -> registerEmployee();
                case 5 -> removeEmployee();
                case 0 -> System.out.println("Exiting Employee Management TUI...");
            }
        }
    }

    private void displayEmployees() {
        List<Employee> employees = employeeService.getEmployees();
        employeeDisplay.displayEmployees(employees);
    }

    private void addEmployee() {
        int id = console.readInt("Enter ID: ");
        String name = console.readString("Enter Name: ");
        int roleChoice = console.readMenuChoice("Enter Role (1-Admin, 2-Cashier): ", 1, 2);

        Employee emp = switch (roleChoice) {
            case 1 -> new Admin(name, id);
            case 2 -> new Cashier(name, id);
            default -> null; // Won’t happen due to validation in ConsoleHelper
        };

        if (employeeService.addEmployee(emp)) {
            System.out.println("Employee added successfully.");
        } else {
            System.out.println("Employee with this ID already exists.");
        }
    }

    private void updateEmployee() {
        int id = console.readInt("Enter Employee ID to update: ");
        Employee oldEmp = employeeService.findEmployee(id);
        if (oldEmp == null) {
            System.out.println("Employee not found.");
            return;
        }

        String newName = console.readString("Enter new Name (" + oldEmp.getName() + "): ");
        if (newName.isBlank()) newName = oldEmp.getName();

        String newRole = console.readString("Change Role (Admin, Cashier, Enter to keep current): ");

        List<Auth> linkedAccounts = accountService.getAccounts().stream()
                .filter(a -> a.getEmp().getId() == oldEmp.getId())
                .toList();

        Employee newEmp = employeeService.updateEmployee(oldEmp, newName, newRole, linkedAccounts);

        System.out.println("Employee updated successfully: " + newEmp.getDetails());
    }

    private void registerEmployee() {
        int empId = console.readInt("Enter Employee ID to register: ");
        String username = console.readString("Enter username: ");
        String password = console.readString("Enter password: ");

        if (accountService.registerEmployee(empId, username, password)) {
            System.out.println("Employee registered successfully.");
        } else {
            System.out.println("Failed to register employee (check ID or username).");
        }
    }

    private void removeEmployee() {
        int id = console.readInt("Enter Employee ID to remove: ");
        if (employeeService.removeEmployee(id)) {
            System.out.println("Employee removed successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }
}
