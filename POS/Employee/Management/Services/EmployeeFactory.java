package POS.Employee.Management.Services;

import POS.Employee.Base.Employee;
import POS.Employee.Staff.Admin;
import POS.Employee.Staff.Cashier;

public class EmployeeFactory {
    public static Employee createEmployee(String role, String name, int id) {
        return switch (role) {
            case "Admin" -> new Admin(name, id);
            case "Cashier" -> new Cashier(name, id);
            default -> throw new IllegalArgumentException("Unknown role: " + role);
        };
    }
}
