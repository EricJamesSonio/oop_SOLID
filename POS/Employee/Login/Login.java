package POS.Employee.Login;

import POS.Employee.Management.Interfaces.IAccountService;

public class Login {
    private IAccountService accountService;

    public Login(IAccountService accountService) {
        this.accountService = accountService;
    }

    public boolean login(String username, String password) {
        if (accountService == null) return false;
        return verifyLogin(username, password);
    }

    private boolean verifyLogin(String username, String password) {
        for (Auth a : accountService.getAccounts()) {
            if (a.getUsername().equals(username) && a.checkPassword(password)) {
                return true;
            }
        }
        return false;
    }
}
