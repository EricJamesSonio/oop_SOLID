package POS.Menu.UI.TUI;

import POS.Common.ConsoleHelper;
import POS.Menu.Base.MenuItem;
import POS.Menu.Services.MenuService;

public class MenuTUI {
    private final MenuService menuService;
    private final ConsoleHelper console;

    public MenuTUI(MenuService menuService, ConsoleHelper console) {
        this.menuService = menuService;
        this.console = console;
    }

    public void showMenu() {
        menuService.showMenu();
    }

    public MenuItem selectMenuItem() {
        menuService.showMenu();
        int code = console.readInt("Enter Menu Code to select: ");
        MenuItem item = menuService.getMenuItemByCode(code);

        if (item == null) {
            System.out.println("Invalid code!");
        }
        return item;
    }
}
