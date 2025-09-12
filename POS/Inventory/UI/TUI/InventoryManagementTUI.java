package POS.Inventory.UI.TUI;

import POS.Common.ConsoleHelper;
import POS.Inventory.Management.InventoryManagement;
import POS.Inventory.Management.InventoryDisplay;
import POS.Inventory.Management.InventoryViewer;

public class InventoryManagementTUI {
    private final InventoryManagement inventory;
    private final InventoryDisplay viewer;
    private final ConsoleHelper console;

    public InventoryManagementTUI(InventoryManagement inventory, InventoryDisplay viewer, ConsoleHelper console) {
        this.inventory = inventory;
        this.viewer = viewer;
        this.console = console;
    }

    public void showMenu() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("\n--- Inventory Management ---");
            System.out.println("1. Add Ingredient Stock");
            System.out.println("2. Deduct Ingredient Stock");
            System.out.println("3. Remove Ingredient");
            System.out.println("4. Display Inventory");
            System.out.println("0. Exit");

            choice = console.readMenuChoice("Choose: ", 0, 4);

            switch (choice) {
                case 1 -> addStock();
                case 2 -> deductStock();
                case 3 -> removeIngredient();
                case 4 -> viewer.display(inventory.getAllIngredientAmounts());
                case 0 -> System.out.println("Exiting Inventory TUI...");
            }
        }
    }

    private void addStock() {
        int code = console.readInt("Enter Ingredient Code: ");
        double amount = console.readDouble("Enter amount to add: ");
        boolean success = inventory.addIngredientAmount(inventory.getIngredient(code), amount);
        System.out.println(success ? "Stock added." : "Failed to add stock.");
    }

    private void deductStock() {
        int code = console.readInt("Enter Ingredient Code: ");
        double amount = console.readDouble("Enter amount to deduct: ");
        boolean success = inventory.deductIngredientAmount(code, amount);
        System.out.println(success ? "Stock deducted." : "Failed to deduct stock.");
    }

    private void removeIngredient() {
        int code = console.readInt("Enter Ingredient Code to remove: ");
        boolean success = inventory.removeIngredientAmount(code);
        System.out.println(success ? "Ingredient removed." : "Failed to remove ingredient.");
    }
}
