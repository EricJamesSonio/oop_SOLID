
package pos.service;

import pos.model.Ingredient;
import java.util.List;

public class IngredientServiceHelper {
    private InventoryService inventory;
    public IngredientServiceHelper(InventoryService inventory){
        this.inventory = inventory;
    }
    // deduct by ingredient name (for drinks)
    public boolean deductDrinkByName(String name, int qty){
        List<Ingredient> all = inventory.all();
        for (Ingredient ing : all){
            if (ing.getName().equalsIgnoreCase(name)){
                if (ing.getStock() < qty) return false;
                inventory.deduct(ing.getId(), qty);
                return true;
            }
        }
        return false;
    }
}
