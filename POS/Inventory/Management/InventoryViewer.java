package POS.Inventory.Management;

import java.util.List;
import POS.Inventory.Ingredients.IngredientAmount;

public class InventoryViewer implements InventoryDisplay {

    @Override
    public void display(List<IngredientAmount> ingredients) {
        if (ingredients.isEmpty()) {
            System.out.println("Inventory is empty");
            return;
        }

        for (IngredientAmount i : ingredients) {
            System.out.println(i.getIngredient()); // make sure Ingredient.toString() is implemented
        }
    }
}
