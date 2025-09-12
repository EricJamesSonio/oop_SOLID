package POS.Menu.Services;

import POS.Menu.Base.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MenuRepository {
    private final List<MenuItem> menuItems;

    public MenuRepository() {
        this.menuItems = new ArrayList<>();
    }

    public void addMenuItem(MenuItem item) {
        menuItems.add(item);
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItems;
    }

    public MenuItem findByCode(int code) {
        return menuItems.stream()
                .filter(item -> item.getCode() == code)
                .findFirst()
                .orElse(null);
    }

    public void displayMenu() {
        System.out.println("--- Menu ---");
        for (MenuItem item : menuItems) {
            System.out.printf("%d - %s : %.2f\n", item.getCode(), item.getName(), item.getPrice());
        }
    }
}
