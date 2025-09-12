package POS.Inventory.Ingredients.Fruits;
import POS.Inventory.Ingredients.Ingredient;

import java.time.LocalDate;

public class Fruit extends Ingredient {
    public Fruit(String name, int code, double price, String unitStock, LocalDate expDate) {
        super(name, code, price, unitStock, expDate);
    }
}

class Apple extends Fruit {
    public Apple(String name, int code, double price, String unitStock, LocalDate expDate) {
        super(name, code, price, unitStock, expDate);
    }
}

class Banana extends Fruit {
    public Banana(String name, int code, double price, String unitStock, LocalDate expDate) {
        super(name, code, price, unitStock, expDate);
    }
}

class Orange extends Fruit {
    public Orange(String name, int code, double price, String unitStock, LocalDate expDate) {
        super(name, code, price, unitStock, expDate);
    }
}
