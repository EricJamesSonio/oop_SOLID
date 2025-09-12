package POS.Employee.Management;

import POS.Common.ConsoleHelper;
import POS.Employee.Management.Services.EmployeeService;
import POS.Employee.Management.Services.AccountService;
import POS.Employee.Management.UI.ConsoleEmployeeDisplay;
import POS.Employee.Management.UI.ConsoleAccountDisplay;
import POS.Employee.Management.UI.TUI.EmployeeManagementTUI;
import POS.Employee.Management.Interfaces.IEmployeeService;
import POS.Employee.Management.Interfaces.IAccountService;
import POS.Employee.Management.Interfaces.IEmployeeDisplay;
import POS.Employee.Management.Interfaces.IAccountDisplay;

public class EmployeeFactoryPlant {

    public static EmployeeManagementTUI createEmployeeManagementTUI() {
        // Services
        IEmployeeService empService = new EmployeeService();
        IAccountService accService = new AccountService(empService);

        // Displays
        IEmployeeDisplay empDisplay = new ConsoleEmployeeDisplay();
        IAccountDisplay accDisplay = new ConsoleAccountDisplay();

        // Console helper
        ConsoleHelper consoleHelper = new ConsoleHelper();

        // TUI
        return new EmployeeManagementTUI(empService, accService, empDisplay, accDisplay, consoleHelper);
    }
}
