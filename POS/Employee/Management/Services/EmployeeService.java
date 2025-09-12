package POS.Employee.Management.Services;

import POS.Employee.Base.Employee;
import POS.Employee.Management.Interfaces.IEmployeeService;
import POS.Employee.Login.Auth;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService implements IEmployeeService {
    private List<Employee> employees = new ArrayList<>();

    @Override
    public boolean addEmployee(Employee emp) {
        if (findEmployee(emp.getId()) != null) return false;
        employees.add(emp);
        return true;
    }

    @Override
    public boolean removeEmployee(int id) {
        Employee emp = findEmployee(id);
        if (emp == null) return false;
        employees.remove(emp);
        return true;
    }

    @Override
    public Employee findEmployee(int id) {
        return employees.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Employee> getEmployees() {
        return List.copyOf(employees);
    }

    // SOLID-friendly update method
    public Employee updateEmployee(Employee oldEmp, String newName, String newRole, List<Auth> linkedAccounts) {
        // Determine new employee type using factory
        Employee newEmp = (newRole == null || newRole.isBlank()) 
                ? EmployeeFactory.createEmployee(oldEmp.getClass().getSimpleName(), newName, oldEmp.getId())
                : EmployeeFactory.createEmployee(newRole, newName, oldEmp.getId());

        // Replace in list
        removeEmployee(oldEmp.getId());
        addEmployee(newEmp);

        // Update auth accounts
        if (linkedAccounts != null) {
            linkedAccounts.forEach(a -> a.setEmployee(newEmp));
        }

        return newEmp;
    }
}
