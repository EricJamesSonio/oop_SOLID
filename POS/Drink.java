package POS;

public class Drink extends MenuItem {
    public Drink (String name, int code, double price) {
        super(name, code, price);
    }

    @Override
    public String getDetails() {
        return "Name : " + getName() + " Code : " + getCode() + " Price : " + getPrice() ;
    }
}

class Water extends Drink {
    public Water (String name, int code , double price) {
        super(name, code, price);
    }
}

class Coke extends Drink {
    public Coke (String name, int code, double price) {
        super(name, code, price);
    }
}

class Royal extends Drink {
    public Royal (String name, int code, double price) {
        super(name, code, price);
    }
}

class Sprite extends Drink {
    public Sprite (String name, int code, double price) {
        super(name, code, price);
    }
}