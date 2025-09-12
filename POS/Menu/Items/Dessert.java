package POS.Menu.Items;
import POS.Menu.Base.MenuItem;
import POS.Menu.Ingredients.Recipe;

import java.time.LocalDate;

public class Dessert extends MenuItem {
    public Dessert(String name, int code, double price, String description, LocalDate expDate, Recipe recipe) {
        super(name, code, price, description, expDate, recipe);
    }

    @Override
    public String getDetails() {
        return "Name: " + getName() +
               " | Code: " + getCode() +
               " | Price: " + getPrice() +
               " | Description: " + getDescription() +
               " | Expiration: " + getExpDate() +
               " | Recipe: " + (getRecipe() != null ? getRecipe() : "No recipe");
    }
}

class IceCream extends Dessert {
    public IceCream(String name, int code, double price, String description, LocalDate expDate, Recipe recipe) {
        super(name, code, price, description, expDate, recipe);
    }
}

class FruitSalad extends Dessert {
    public FruitSalad(String name, int code, double price, String description, LocalDate expDate, Recipe recipe) {
        super(name, code, price, description, expDate, recipe);
    }
}
