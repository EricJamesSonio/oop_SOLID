package POS.Employee.Management;
import POS.Employee.Base.Employee;

public class Auth {
    private int id;
    private Employee emp;
    private String username;
    private String password;

    public Auth (int id,Employee emp, String username, String password) {
        this.id = id;
        this.emp = emp;
        this.username = username;
        this.password = password;
    } 

    public void changeUsername(String newUsername) {
        username = newUsername;
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        if (oldPassword.equals(password)) {
            password = newPassword;
            return true;
        } else {
            return false;
        }
    }

    public Employee getEmp() {
        return emp;
    }

    public String getEmpDetails() {
        return emp.getDetails();
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public boolean checkPassword(String inputPassword) {
        return inputPassword.equals(password);
    }

    public String getDetails() {
        return String.format("Username : %s | Id : %d ", username, id);
    }
}
