package POS.Inventory.Management;

import java.util.List;
import POS.Inventory.Ingredients.IngredientAmount;

public interface InventoryDisplay {
    void display(List<IngredientAmount> ingredients);
}
