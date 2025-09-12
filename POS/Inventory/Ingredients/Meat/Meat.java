package POS.Inventory.Ingredients.Meat;
import POS.Inventory.Ingredients.Ingredient;

import java.time.LocalDate;

public class Meat extends Ingredient {
    public Meat(String name, int code, double price, String unitStock, LocalDate expDate) {
        super(name, code, price, unitStock, expDate);
    }
}

class Beef extends Meat {
    public Beef(String name, int code, double price, String unitStock, LocalDate expDate) {
        super(name, code, price, unitStock, expDate);
    }
}

class Pork extends Meat {
    public Pork(String name, int code, double price, String unitStock, LocalDate expDate) {
        super(name, code, price, unitStock, expDate);
    }
}
