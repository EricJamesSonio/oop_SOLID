package POS.Inventory.Seed;

import POS.Common.ConsoleHelper;
import POS.Inventory.UI.TUI.InventoryManagementTUI;
import POS.Inventory.Management.InventoryManagement;
import POS.Inventory.Management.InventoryViewer;

public class InventoryFactoryPlant {

    public static InventoryManagementTUI createInventoryTUI() {
        InventoryManagement inventory = new InventoryManagement();
        InventoryViewer viewer = new InventoryViewer();
        ConsoleHelper console = new ConsoleHelper();

        return new InventoryManagementTUI(inventory, viewer, console);
    }
}
