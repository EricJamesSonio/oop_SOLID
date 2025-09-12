package POS.Orders.Management;

import POS.Common.ConsoleHelper;
import POS.Menu.Services.MenuRepository;
import POS.Menu.Services.MenuService;
import POS.Orders.UI.TUI.OrderManagementTUI;

public class OrderTUIFactoryPlant {

    public static OrderManagementTUI createOrderManagementTUI() {
        // Use existing facade factory
        OrderManagementFacade facade = OrderFactoryPlant.createOrderManagementFacade();

        // Menu service + repository
        MenuRepository menuRepository = new MenuRepository();
        MenuService menuService = new MenuService(menuRepository);

        // Console helper
        ConsoleHelper console = new ConsoleHelper();

        // Inject all dependencies into TUI
        return new OrderManagementTUI(facade, menuService, console);
    }
}
