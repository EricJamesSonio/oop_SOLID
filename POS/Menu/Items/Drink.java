package POS.Menu.Items;
import POS.Inventory.Ingredients.Recipe;
import POS.Menu.Base.MenuItem;

import java.time.LocalDate;

public class Drink extends MenuItem {
    public Drink(String name, int code, double price, String description, LocalDate expDate, Recipe recipe) {
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

class Water extends Drink {
    public Water(String name, int code, double price, String description, LocalDate expDate, Recipe recipe) {
        super(name, code, price, description, expDate, recipe);
    }
}

class Coke extends Drink {
    public Coke(String name, int code, double price, String description, LocalDate expDate, Recipe recipe) {
        super(name, code, price, description, expDate, recipe);
    }
}

class Royal extends Drink {
    public Royal(String name, int code, double price, String description, LocalDate expDate, Recipe recipe) {
        super(name, code, price, description, expDate, recipe);
    }
}

class Sprite extends Drink {
    public Sprite(String name, int code, double price, String description, LocalDate expDate, Recipe recipe) {
        super(name, code, price, description, expDate, recipe);
    }
}
