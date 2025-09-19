package pos.service;

import pos.model.Order;
import pos.model.OrderItem;
import pos.model.MenuItem;
import pos.model.Ingredient;
import java.util.*;
/**
 * InventoryManager is responsible for checking and deducting ingredients for orders.
 * This extracts inventory-related responsibilities from OrderService (SRP).
 */
public class InventoryManager {
    private InventoryService inventory;
    private MenuService menuService;

    public InventoryManager(InventoryService inventory, MenuService menuService){
        this.inventory = inventory;
        this.menuService = menuService;
    }

    /**
     * Attempts to deduct ingredients required for the order.
     * Returns a list of problem messages (empty if ok).
     */
    public List<String> tryDeductIngredients(Order o){
        List<String> problems = new ArrayList<String>();
        for (OrderItem it : o.getItems()){
            MenuItem mi = menuService.findById(it.getMenuItemId());
            if (mi == null) { problems.add("Menu item not found: " + it.getMenuItemName()); continue; }
            if (mi.getRecipe() == null || mi.getRecipe().getItems().isEmpty()){
                // drink - we expect there to be an ingredient representing the drink stock with same name
                // find ingredient by name
                IngredientServiceHelper helper = new IngredientServiceHelper(inventory);
                boolean ok = helper.deductDrinkByName(mi.getName(), it.getQuantity());
                if (!ok) problems.add("Insufficient stock for drink: " + mi.getName());
            } else {
                Map<Integer,Integer> map = mi.getRecipe().getItems();
                for (Map.Entry<Integer,Integer> e : map.entrySet()){
                    int ingId = e.getKey();
                    int needPerPortion = e.getValue();
                    int totalNeeded = needPerPortion * it.getQuantity();
                    Ingredient ing = inventory.findById(ingId);
                    if (ing == null){
                        problems.add("Missing ingredient id: " + ingId + " for menu item: " + mi.getName());
                        continue;
                    }
                    if (ing.getStock() < totalNeeded){
                        problems.add("Insufficient stock for " + ing.getName() + " for menu item: " + mi.getName());
                    } else {
                        // deduct
                        inventory.deduct(ingId, totalNeeded);
                    }
                }
            }
        }
        return problems;
    }
}