package POS.Employee.Management.UI;

import POS.Employee.Base.Employee;
import POS.Employee.Management.Interfaces.IEmployeeDisplay;
import java.util.List;

public class ConsoleEmployeeDisplay implements IEmployeeDisplay {
    @Override
    public void displayEmployees(List<Employee> employees) {
        for (Employee e : employees) {
            System.out.println(e.getDetails());
        }
    }
}
