package POS.Menu.Tester;

import POS.Menu.Base.MenuItem;
import POS.Menu.Services.MenuFactoryPlant;
import POS.Menu.Services.MenuRepository;
import POS.Menu.Services.MenuService;
import POS.Menu.UI.TUI.MenuTUI;
import POS.Common.ConsoleHelper;

import java.util.List;

public class MainMenuTester {
    public static void main(String[] args) {
        // 1️⃣ Load menu items from JSON
        String jsonPath = "POS/resources/data/menu.json"; // adjust path if needed
        MenuFactoryPlant factory = new MenuFactoryPlant(jsonPath);
        List<MenuItem> loadedItems = factory.getAllMenuItems();

        // 2️⃣ Prepare repository and add loaded items
        MenuRepository repository = new MenuRepository();
        for (MenuItem item : loadedItems) {
            repository.addMenuItem(item);
        }

        // 3️⃣ Wrap repository with service
        MenuService menuService = new MenuService(repository);

        // 4️⃣ Console helper for consistent input handling
        ConsoleHelper console = new ConsoleHelper();

        // 5️⃣ Launch TUI
        MenuTUI menuTUI = new MenuTUI(menuService, console);

        System.out.println("\n=== MENU TESTER ===\n");

        // 6️⃣ Show menu
        menuTUI.showMenu();

        // 7️⃣ Let user select an item
        MenuItem selected = menuTUI.selectMenuItem();
        if (selected != null) {
            System.out.println("\nYou selected:");
            System.out.println(selected.getDetails());
        } else {
            System.out.println("No valid selection made.");
        }
    }
}
