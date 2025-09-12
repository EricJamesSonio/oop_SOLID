package POS.Inventory.Ingredients;

import java.util.*;

public class Recipe {
    private List<IngredientAmount> ingredients;

    public Recipe() {
        this.ingredients = new ArrayList<>();
    }

    public List<IngredientAmount> getIngredient() {
        return ingredients;
    }

    @Override
    public String toString() {
        if (ingredients.isEmpty()) return "No ingredients";
        StringBuilder sb = new StringBuilder();
        for (IngredientAmount ia : ingredients) {
            sb.append(ia.getIngredient().getName())
              .append(" x")
              .append(ia.getAmount())
              .append(", ");
        }
        // remove the trailing comma and space
        return sb.substring(0, sb.length() - 2);
    }
}
