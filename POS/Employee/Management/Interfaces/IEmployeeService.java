package POS.Employee.Management.Interfaces;
import POS.Employee.Base.Employee;
import POS.Employee.Login.Auth;
import java.util.List;

public interface IEmployeeService {
    boolean addEmployee(Employee emp);
    boolean removeEmployee(int id);
    Employee findEmployee(int id);
    List<Employee> getEmployees();
    Employee updateEmployee(Employee oldEmp, String newName, String newRole, List<Auth> linkedAccounts);
}
