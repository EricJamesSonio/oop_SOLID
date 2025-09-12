package POS.Inventory.Tester;

import POS.Common.ConsoleHelper;
import POS.Inventory.Seed.InventoryFactoryPlant;
import POS.Inventory.Management.InventoryManagement;
import POS.Inventory.Ingredients.Ingredient;
import POS.Inventory.Loader.IngredientJsonLoader;
import POS.Inventory.UI.TUI.InventoryManagementTUI;

public class MainInventoryTester {
    public static void main(String[] args) {
        System.out.println("\n=== INVENTORY TESTER ===\n");

        // 1️⃣ Create inventory management instance
        InventoryManagement inventory = new InventoryManagement();

        // 2️⃣ Load ingredients from JSON
        String ingredientsJsonPath = "POS/resources/data/ingredients.json";
        IngredientJsonLoader loader = new IngredientJsonLoader(inventory, ingredientsJsonPath);
        System.out.println("✅ Ingredients loaded from JSON.");

        // 3️⃣ Display all loaded ingredients
        System.out.println("\n📦 Current Inventory:");
        inventory.getAllIngredientAmounts().forEach(ia ->
                System.out.println("Code: " + ia.getIngredient().getCode() +
                        " | Name: " + ia.getIngredient().getName() +
                        " | Stock: " + ia.getAmount())
        );

        // 4️⃣ Test: Add stock to first ingredient (if exists)
        if (!inventory.getAllIngredientAmounts().isEmpty()) {
            Ingredient first = inventory.getAllIngredientAmounts().get(0).getIngredient();
            System.out.println("\n➕ Adding 50 units to: " + first.getName());
            inventory.addIngredientAmount(first, 50);
            System.out.println("New stock: " + inventory.findIngredientAmount(first.getCode()).getAmount());
        }

        // 5️⃣ Test: Deduct stock from first ingredient
        if (!inventory.getAllIngredientAmounts().isEmpty()) {
            Ingredient first = inventory.getAllIngredientAmounts().get(0).getIngredient();
            System.out.println("\n➖ Deducting 20 units from: " + first.getName());
            inventory.deductIngredientAmount(first.getCode(), 20);
            System.out.println("New stock: " + inventory.findIngredientAmount(first.getCode()).getAmount());
        }

        // 6️⃣ Test: Remove ingredient
        if (!inventory.getAllIngredientAmounts().isEmpty()) {
            Ingredient first = inventory.getAllIngredientAmounts().get(0).getIngredient();
            System.out.println("\n🗑 Removing ingredient: " + first.getName());
            inventory.removeIngredientAmount(first.getCode());
        }

        // 7️⃣ Optional: Launch TUI
        ConsoleHelper console = new ConsoleHelper();
        InventoryManagementTUI tui = InventoryFactoryPlant.createInventoryTUI();
        System.out.println("\n💻 Launching Inventory TUI (interactive)...");
        tui.showMenu();
    }
}
