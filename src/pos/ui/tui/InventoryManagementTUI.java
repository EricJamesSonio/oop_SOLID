package pos.ui.tui;

import pos.ui.UI;
import pos.model.Ingredient;
import pos.service.InventoryService;
import pos.service.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InventoryManagementTUI implements UI {
    private InventoryService inventory;
    private Scanner scanner = new Scanner(System.in);

    public InventoryManagementTUI(InventoryService inventory) {
        this.inventory = inventory;
    }

    @Override
    public void start() {
        while (true) {
            System.out.println("\n--- INVENTORY MANAGEMENT ---");
            System.out.println("1) View Ingredients");
            System.out.println("2) Add Stock to Ingredient");
            System.out.println("3) Remove Ingredient");
            System.out.println("4) Update Ingredient Stock");
            System.out.println("0) Back");
            System.out.print("Choose: ");
            String c = scanner.nextLine().trim();

            if (c.equals("1")) viewIngredients();
            else if (c.equals("2")) addStock();
            else if (c.equals("3")) removeIngredient();
            else if (c.equals("4")) updateIngredientStock();
            else if (c.equals("0")) return;
            else System.out.println("Invalid");
        }
    }

    private void viewIngredients() {
        System.out.println("\n--- INGREDIENTS ---");
        for (Ingredient ing : inventory.all()) {
            System.out.println(ing.toString());
        }
    }

    private void addStock() {
        viewIngredients();
        System.out.print("Ingredient id: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        Ingredient ing = inventory.findById(id);
        if (ing == null) {
            System.out.println("Not found");
            return;
        }

        System.out.print("Add quantity (positive integer): ");
        int q = Integer.parseInt(scanner.nextLine().trim());
        if (q <= 0) {
            System.out.println("Invalid quantity");
            return;
        }

        inventory.increase(id, q);
        System.out.println("Added stock.");
    }

    private void removeIngredient() {
        viewIngredients();
        System.out.print("Ingredient id to remove: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        List<Ingredient> list = inventory.all();
        List<String> out = new ArrayList<>();
        for (Ingredient i : list) {
            if (i.getId() != id) {
                out.add(i.getId() + "|" + i.getName() + "|" + i.getStock() +
                        "|" + i.getUnit() + "|" + i.getThreshold());
            }
        }
        FileUtil.writeAll("resources/data/ingredientsStock.txt", out);
        inventory.load();
        System.out.println("Removed if existed.");
    }

    private void updateIngredientStock() {
        viewIngredients();
        System.out.print("Ingredient id to update: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        Ingredient ing = inventory.findById(id);
        if (ing == null) {
            System.out.println("Not found");
            return;
        }

        System.out.print("Enter new stock (integer >= 0): ");
        int q = Integer.parseInt(scanner.nextLine().trim());
        if (q < 0) {
            System.out.println("Invalid");
            return;
        }

        ing.setStock(q);
        inventory.saveAll();
        System.out.println("Updated.");
    }
}
