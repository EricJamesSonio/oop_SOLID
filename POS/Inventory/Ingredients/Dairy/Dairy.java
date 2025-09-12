package POS.Inventory.Ingredients.Dairy;
import POS.Inventory.Ingredients.Ingredient;

import java.time.LocalDate;

public class Dairy extends Ingredient {
    public Dairy(String name, int code, double price, String unitStock, LocalDate expDate) {
        super(name, code, price, unitStock, expDate);
    }
}

class Milk extends Dairy {
    public Milk(String name, int code, double price, String unitStock, LocalDate expDate) {
        super(name, code, price, unitStock, expDate);
    }
}

class Cheese extends Dairy {
    public Cheese(String name, int code, double price, String unitStock, LocalDate expDate) {
        super(name, code, price, unitStock, expDate);
    }
}

class Butter extends Dairy {
    public Butter(String name, int code, double price, String unitStock, LocalDate expDate) {
        super(name, code, price, unitStock, expDate);
    }
}
