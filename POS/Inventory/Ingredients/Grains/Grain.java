package POS.Inventory.Ingredients.Grains;
import POS.Inventory.Ingredients.Ingredient;

import java.time.LocalDate;

public class Grain extends Ingredient {
    public Grain(String name, int code, double price, String unitStock, LocalDate expDate) {
        super(name, code, price, unitStock, expDate);
    }
}

class Rice extends Grain {
    public Rice(String name, int code, double price, String unitStock, LocalDate expDate) {
        super(name, code, price, unitStock, expDate);
    }
}

class Wheat extends Grain {
    public Wheat(String name, int code, double price, String unitStock, LocalDate expDate) {
        super(name, code, price, unitStock, expDate);
    }
}
