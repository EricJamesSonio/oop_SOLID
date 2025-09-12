package POS.Employee.Management.Services;

import POS.Employee.Management.Interfaces.IAccountService;
import POS.Employee.Management.Interfaces.IEmployeeService;
import POS.Employee.Base.Employee;
import POS.Employee.Login.Auth;

import java.util.*;

public class AccountService implements IAccountService {
    private List<Auth> accounts = new ArrayList<>();
    private static int nextId = 1;
    private IEmployeeService employeeService;

    public AccountService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public boolean registerEmployee(int empId, String username, String password) {
        Employee emp = employeeService.findEmployee(empId);
        if (emp == null || hasAccount(empId) || usernameExist(username)) return false;

        Auth account = new Auth(nextId++, emp, username, password);
        accounts.add(account);
        return true;
    }

    @Override
    public boolean hasAccount(int empId) {
        return accounts.stream().anyMatch(a -> a.getEmp().getId() == empId);
    }

    @Override
    public boolean usernameExist(String username) {
        return accounts.stream().anyMatch(a -> a.getUsername().equals(username));
    }

    @Override
    public Auth findAuth(int id) {
        return accounts.stream().filter(a -> a.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Auth> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }
}
