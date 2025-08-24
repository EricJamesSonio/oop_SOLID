package POS;

public class MainDish extends MenuItem {
    public MainDish (String name, int code, double price) {
        super(name, code, price);
    }

    @Override
    public String getDetails() {
        return "Name : " + getName() + " Code : " + getCode() + " Price : " + getPrice() ;
    }
}

class ChickenDish extends MainDish{
    public ChickenDish(String name, int code, double price) {
        super(name, code , price);
    }
}

class FriedChickenLeg extends ChickenDish {
    public FriedChickenLeg (String name, int code, double price) {
        super(name, code, price);
    }
}

class FriedChickenBreast extends ChickenDish {
    public FriedChickenBreast (String name, int code, double price) {
        super(name, code, price);
    }
}

class FriedChickenWhole extends ChickenDish {
    public FriedChickenWhole (String name, int code, double price) {
        super(name, code, price);
    }
}