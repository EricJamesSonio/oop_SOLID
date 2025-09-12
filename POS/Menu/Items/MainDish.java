package POS.Menu.Items;
import POS.Menu.Base.MenuItem;
import POS.Menu.Ingredients.Recipe;

import java.time.LocalDate;

public class MainDish extends MenuItem {
    public MainDish(String name, int code, double price, String description, LocalDate expDate, Recipe recipe) {
        super(name, code, price, description, expDate, recipe);
    }

    @Override
    public String getDetails() {
        return "Name : " + getName() +
               " | Code : " + getCode() +
               " | Price : " + getPrice() +
               " | Description : " + getDescription() +
               " | Expiration : " + getExpDate();
    }
}

class ChickenDish extends MainDish {
    public ChickenDish(String name, int code, double price, String description, LocalDate expDate, Recipe recipe) {
        super(name, code, price, description, expDate, recipe);
    }
}

class FriedChickenLeg extends ChickenDish {
    public FriedChickenLeg(String name, int code, double price, String description, LocalDate expDate, Recipe recipe) {
        super(name, code, price, description, expDate, recipe);
    }
}

class FriedChickenBreast extends ChickenDish {
    public FriedChickenBreast(String name, int code, double price, String description, LocalDate expDate, Recipe recipe) {
        super(name, code, price, description, expDate, recipe);
    }
}

class FriedChickenWhole extends ChickenDish {
    public FriedChickenWhole(String name, int code, double price, String description, LocalDate expDate, Recipe recipe) {
        super(name, code, price, description, expDate, recipe);
    }
}
