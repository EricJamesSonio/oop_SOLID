package POS.Employee.Management.Interfaces;

import java.util.List;

import POS.Employee.Login.Auth;

public interface IAccountService {
    boolean registerEmployee(int empId, String username, String password);
    boolean hasAccount(int empId);
    boolean usernameExist(String username);
    Auth findAuth(int id);
    List<Auth> getAccounts();
}
