package POS;
import java.util.*;

public class Recipe {
    private List<IngredientAmount> ingredients;

    public Recipe () {
        this.ingredients = new ArrayList<>();
    }

    public List<IngredientAmount> getIngredient() {
        return ingredients;
    }

}
