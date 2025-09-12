package POS.Menu.Services;

import POS.Menu.Base.MenuItem;

import java.util.List;

public class MenuService {
    private final MenuRepository repository;

    public MenuService(MenuRepository repository) {
        this.repository = repository;
    }

    public void addMenuItem(MenuItem item) {
        repository.addMenuItem(item);
    }

    public List<MenuItem> getMenuItems() {
        return repository.getAllMenuItems();
    }

    public MenuItem getMenuItemByCode(int code) {
        return repository.findByCode(code);
    }

    public void showMenu() {
        repository.displayMenu();
    }
}
