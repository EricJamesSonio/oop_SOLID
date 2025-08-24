package POS;

public class Ingredient {
    private String name;
    private int code;    
    private double price;
    private String unitStock;

    public Ingredient (String name, int code, double price, String unitStock) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.unitStock = unitStock;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public double getPrice() {
        return price;
    }

    public String getUnit() {
        return unitStock;
    }
}
