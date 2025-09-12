package POS.Employee.Management.Interfaces;

import java.util.List;

import POS.Employee.Login.Auth;

public interface IAccountDisplay {
    void displayAccounts(List<Auth> accounts);
}
