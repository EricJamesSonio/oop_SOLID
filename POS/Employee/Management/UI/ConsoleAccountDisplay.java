package POS.Employee.Management.UI;

import POS.Employee.Login.Auth;
import POS.Employee.Management.Interfaces.IAccountDisplay;
import java.util.List;

public class ConsoleAccountDisplay implements IAccountDisplay {
    @Override
    public void displayAccounts(List<Auth> accounts) {
        for (Auth a : accounts) {
            System.out.println(a.getDetails());
        }
    }
}
