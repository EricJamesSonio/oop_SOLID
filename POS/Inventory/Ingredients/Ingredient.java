package POS.Inventory.Ingredients;

import java.time.LocalDate;

public class Ingredient {
    private String name;
    private int code;    
    private double price;
    private String unitStock;
    private LocalDate expDate;

    public Ingredient (String name, int code, double price, String unitStock, LocalDate expDate) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.unitStock = unitStock;
        this.expDate = expDate;
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

    public String getUnitStock() {
        return unitStock;
    }

    public LocalDate getExpdate() {
        return expDate;
    }

    @Override
    public String toString () {
        return String.format("Name : %s , Code : %d , Price : %.2f , Unit : %s , Expiration Date :", name, code, price, unitStock, expDate);
    }
}
