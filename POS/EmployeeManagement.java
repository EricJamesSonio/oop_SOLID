package POS;

import java.util.*;

public class EmployeeManagement {
    private List<Employee> employees;
    private List<Auth> accounts;
    private static int nextId = 1;

    public EmployeeManagement () {
        this.employees = new ArrayList<>();
        this.accounts = new ArrayList<>();
    }

    public boolean addEmployee(Employee emp) {
        Employee existing = findEmployee(emp.getId());

        if (existing != null) {
            return false;
        }

        employees.add(emp);
        return true;
    }

    public boolean removeEmployee(int id) {
        Employee existing = findEmployee(id);
        if (existing == null) {
            return false;
        }

        employees.remove(existing);

        accounts.removeIf(a -> a.getEmp().getId() == id);

        return true;
    }


    public boolean registerEmployee(int empId, String username, String password) {
        Employee existing = findEmployee(empId);

        if (existing == null) {
            return false;
        }

        if (hasAccount(empId) == true) {
            return false;
        }

        if (usernameExist(username) == true) {
            return false;
        }
        int id = nextId++;

        

        Auth account = new Auth(id,existing, username, password);
        accounts.add(account);
        return true;
    }

    public boolean hasAccount(int id) {
        for (Auth a : accounts) {
            if (a.getEmp().getId() == id) {
                return true;
            }
        }
        return false;
        
    }

    public boolean usernameExist(String userName) {
        for (Auth a : accounts) {
            if (a.getUsername().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public Auth findAuth(int id) {
        for (Auth a : accounts) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    public Employee findEmployee(int id) {
        for (Employee emp : employees) {
            if (emp.getId() == id) {
                return emp;
            }
        }
        return null;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Auth> getAccounts() {
        return accounts;
    }

}

class ManagementViewer {
    private EmployeeManagement management;

    public ManagementViewer(EmployeeManagement management) {
        this.management = management;
    }

    public void displayEmployees() {
        for (Employee emp : management.getEmployees()) {
            System.out.println(emp.getDetails());
        }
    }

    public void displayAccounts() {
        for (Auth a : management.getAccounts()) {
            System.out.println(a.getDetails());
        }
    }

    public EmployeeManagement getManagement() {
        return management;
    }
} 