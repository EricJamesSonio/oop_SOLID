package POS;

public class Dessert extends MenuItem {
    public Dessert (String name, int code, double price) {
        super(name, code, price);
    }

    @Override
    public String getDetails() {
        return "Name : " + getName() + " Code : " + getCode() + " Price : " + getPrice() ;
    }
}

class IceCream extends Dessert {
    public IceCream (String name, int code, double price) {
        super(name, code, price);
    }
}

class FruitSalad extends Dessert {
    public FruitSalad (String name, int code , double price) {
        super(name, code, price);
    }
}

