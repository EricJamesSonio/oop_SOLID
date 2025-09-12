package POS.Inventory.Ingredients.Poultry;
import POS.Inventory.Ingredients.Ingredient;

import java.time.LocalDate;

public class Poultry extends Ingredient {
    public Poultry(String name, int code, double price, String unitStock, LocalDate expDate) {
        super(name, code, price, unitStock, expDate);
    }
}

class Chicken extends Poultry {
    public Chicken(String name, int code, double price, String unitStock, LocalDate expDate) {
        super(name, code, price, unitStock, expDate);
    }
}

class Turkey extends Poultry {
    public Turkey(String name, int code, double price, String unitStock, LocalDate expDate) {
        super(name, code, price, unitStock, expDate);
    }
}
