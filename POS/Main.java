package POS;
import POS.Employee.Management.EmployeeFactoryPlant;
import POS.Employee.Management.UI.TUI.EmployeeManagementTUI;

public class Main {
    public static void main(String[] args) {
        EmployeeManagementTUI tui = EmployeeFactoryPlant.createEmployeeManagementTUI();
        tui.showMenu();
    }
}
