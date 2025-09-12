package POS.Inventory.Management;

import POS.Inventory.Ingredients.Ingredient;

public interface InventoryWritable {
    boolean addIngredientAmount(Ingredient ingredient, double amount);
    boolean deductIngredientAmount(int code, double amount);
    boolean removeIngredientAmount(int code);
}
