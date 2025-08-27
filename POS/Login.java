package POS;

public class Login {
    private EmployeeManagement empManagement;

    public Login(EmployeeManagement empManagement) {
        this.empManagement = empManagement;
    }

    public boolean login(String username, String password) {
        if (empManagement == null) {
            return false;
        }
        return verifyLogin(username, password);
    }

    


    public boolean verifyLogin(String username, String password) {
        for (Auth a : empManagement.getAccounts()) {
            if (a.getUsername().equals(username) && a.checkPassword(password)) {
                return true;
            }
        }
        return false;
    }
}




