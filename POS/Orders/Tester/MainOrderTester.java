package POS.Orders.Tester;

import POS.Menu.Base.MenuItem;
import POS.Menu.Services.MenuFactoryPlant;
import POS.Menu.Services.MenuRepository;
import POS.Menu.Services.MenuService;
import POS.Orders.Management.Loader.OrderJsonLoader;
import POS.Orders.Management.OrderFactoryPlant;
import POS.Orders.Management.OrderManagementFacade;
import POS.Orders.Management.OrderRepository;
import POS.Orders.Models.Order;

import java.util.List;

public class MainOrderTester {
    public static void main(String[] args) {
        System.out.println("\n=== ORDER TESTER ===\n");

        // 1️⃣ Load menu items (needed so orders can resolve products by code)
        String menuJsonPath = "POS/resources/data/menu.json";
        MenuFactoryPlant menuFactory = new MenuFactoryPlant(menuJsonPath);
        List<MenuItem> menuItems = menuFactory.getAllMenuItems();

        MenuRepository menuRepo = new MenuRepository();
        for (MenuItem item : menuItems) {
            menuRepo.addMenuItem(item);
        }
        MenuService menuService = new MenuService(menuRepo);

        // 2️⃣ Load orders from JSON
        String ordersJsonPath = "POS/resources/data/orders.json";
        OrderJsonLoader loader = new OrderJsonLoader(ordersJsonPath, menuService);
        OrderRepository loadedOrders = loader.toRepository();

        // 3️⃣ Build facade with preloaded orders
        OrderManagementFacade facade = OrderFactoryPlant.createOrderManagementFacade(loadedOrders);

        // 4️⃣ Display all loaded orders
        System.out.println("📦 Loaded Orders:");
        facade.displayOrders();

        // 5️⃣ Test: Process first order (if available)
        if (!loadedOrders.getOrders().isEmpty()) {
            Order first = loadedOrders.getOrders().get(0);
            System.out.println("\n🔎 Processing Order ID " + first.getId());
            boolean success = facade.processOrder(first.getId(), 5000); // give some payment
            System.out.println(success
                    ? "✅ Order processed successfully!"
                    : "❌ Failed to process order.");
        } else {
            System.out.println("⚠️ No orders available in repository.");
        }
    }
}
