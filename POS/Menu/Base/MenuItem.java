package POS.Menu.Base;
import POS.Menu.Ingredients.Recipe;

import java.time.LocalDate;

public abstract class MenuItem {
    private String name;
    private int code;
    private double price;
    private String description;
    private LocalDate expDate;
    private Recipe recipe;

    public MenuItem (String name, int code, double price, String description, LocalDate expDate, Recipe recipe) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.description = description;
        this.expDate = expDate;
        this.recipe = recipe;
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

    public String getDescription() {
        return description;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public abstract String getDetails();

    @Override
    public String toString() {
        return String.format("Name : %s , Code : %d , Price : %.2f , Description : %s ", name, code, price, description);
    }
}





