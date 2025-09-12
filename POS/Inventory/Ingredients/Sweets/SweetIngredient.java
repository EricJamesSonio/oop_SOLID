package POS.Inventory.Ingredients.Sweets;
import POS.Inventory.Ingredients.Ingredient;

import java.time.LocalDate;

public class SweetIngredient extends Ingredient {
    public SweetIngredient(String name, int code, double price, String unitStock, LocalDate expDate) {
        super(name, code, price, unitStock, expDate);
    }
}

class Chocolate extends SweetIngredient {
    public Chocolate(String name, int code, double price, String unitStock, LocalDate expDate) {
        super(name, code, price, unitStock, expDate);
    }
}

class IceCreamIngredient extends SweetIngredient {
    public IceCreamIngredient(String name, int code, double price, String unitStock, LocalDate expDate) {
        super(name, code, price, unitStock, expDate);
    }
}

class Sugar extends SweetIngredient {
    public Sugar(String name, int code, double price, String unitStock, LocalDate expDate) {
        super(name, code, price, unitStock, expDate);
    }
}
