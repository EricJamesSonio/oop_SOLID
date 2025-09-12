package POS.Inventory.Management;

import java.util.List;
import POS.Inventory.Ingredients.Ingredient;
import POS.Inventory.Ingredients.IngredientAmount;

public interface InventoryReadable {
    IngredientAmount findIngredientAmount(int code);
    Ingredient getIngredient(int code);
    List<IngredientAmount> getAllIngredientAmounts();
}
