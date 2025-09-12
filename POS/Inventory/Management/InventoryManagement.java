package POS.Inventory.Management;

import java.util.*;
import POS.Inventory.Ingredients.Ingredient;
import POS.Inventory.Ingredients.IngredientAmount;

public class InventoryManagement implements InventoryReadable, InventoryWritable {

    private final Map<Integer, IngredientAmount> ingredientMap;

    public InventoryManagement() {
        this.ingredientMap = new HashMap<>();
    }

    @Override
    public boolean addIngredientAmount(Ingredient newIngredient, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        IngredientAmount existing = ingredientMap.get(newIngredient.getCode());
        if (existing != null) {
            existing.addAmount(amount);
        } else {
            ingredientMap.put(newIngredient.getCode(), new IngredientAmount(newIngredient, amount));
        }
        return true;
    }

    @Override
    public boolean deductIngredientAmount(int code, double amount) {
        IngredientAmount existing = ingredientMap.get(code);
        if (existing == null || amount <= 0) return false;
        try {
            existing.deductAmount(amount);
        } catch (IllegalStateException e) {
            return false; // not enough stock
        }
        if (existing.getAmount() <= 0) ingredientMap.remove(code);
        return true;
    }

    @Override
    public boolean removeIngredientAmount(int code) {
        return ingredientMap.remove(code) != null;
    }

    @Override
    public IngredientAmount findIngredientAmount(int code) {
        return ingredientMap.get(code);
    }

    @Override
    public Ingredient getIngredient(int code) {
        IngredientAmount ia = ingredientMap.get(code);
        return (ia != null) ? ia.getIngredient() : null;
    }

    @Override
    public List<IngredientAmount> getAllIngredientAmounts() {
        return Collections.unmodifiableList(new ArrayList<>(ingredientMap.values()));
    }
}
