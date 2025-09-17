package POS;
import POS.Employee.Management.EmployeeFactoryPlant;
import POS.Employee.Management.UI.TUI.EmployeeManagementTUI;
// Working palce
public class Main {
    public static void main(String[] args) {
        EmployeeManagementTUI tui = EmployeeFactoryPlant.createEmployeeManagementTUI();
        tui.showMenu();
    }
}
