package POS.Inventory.Management;
import POS.Menu.Ingredients.IngredientAmount;
import POS.Menu.Ingredients.Ingredient;
import java.util.*;

public class InventoryManagement {
    private List<IngredientAmount> ingredients;
    private InventoryViewer viewer;

    public InventoryManagement() {
        this.ingredients = new ArrayList<>();
        this.viewer = new InventoryViewer(this);
    }

    public boolean addIngredientAmount(Ingredient newIngredient, double amount) {
        IngredientAmount existing = findIngredientAmount(newIngredient.getCode());
        if (existing != null) {
            existing.addAmount(amount);
            return true;
        }
        IngredientAmount newIngredientAmount = new IngredientAmount(newIngredient, amount);
        ingredients.add(newIngredientAmount);
        return true;
    }

    public boolean deductIngredientAmount(int code, double value) {
        IngredientAmount existing = findIngredientAmount(code);
        if (existing == null) {
            return false; 
        }
        if (value > existing.getAmount()) {
            return false;
        }
        existing.deductAmount(value);
        if (existing.getAmount() <= 0) {
            ingredients.remove(existing);
        }
        return true;
    }



    public boolean removeIngredientAmount(int code) {
        IngredientAmount existing = findIngredientAmount(code);
        if (existing != null) {
            ingredients.remove(existing);
            return true;
        }
        return false;
    }

    public IngredientAmount findIngredientAmount(int code) {
        for (IngredientAmount i : ingredients) {
            if (i.getIngredientCode() == code) {
                return i;
            }
        }
        return null;
    }

    public Ingredient getIngredient(int id) {
        for (IngredientAmount i : ingredients) {
            if (i.getIngredient().getCode() == id) {
                return i.getIngredient();
            }
        }
        return null;
    }

    public List<IngredientAmount> getAllIngredientAmounts() {
        return ingredients;
    }

    public void displayInventory() {
        viewer.displayInventoryManagement();
    }
}

class InventoryViewer {
    private InventoryManagement management;

    public InventoryViewer (InventoryManagement management) {
        this.management = management;
    }

    public void displayInventoryManagement() {
        for (IngredientAmount i : management.getAllIngredientAmounts()) {
            System.out.println(i.getIngredient());
            }
    }

    public InventoryManagement getManagement() {
        return management;
    }


}