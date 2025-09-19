
package pos.model;

import java.math.BigDecimal;

public class MenuItem {
    protected int id;
    protected String name;
    protected double price;
    protected Recipe recipe; // null or empty for drinks
    public MenuItem(int id, String name, double price, Recipe recipe){
        this.id = id; this.name = name; this.price = price; this.recipe = recipe;
    }
    public int getId(){ return id; }
    public String getName(){ return name; }
    public double getPrice(){ return price; }
    public Recipe getRecipe(){ return recipe; }
    public String getCategory(){ return "MenuItem"; }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return id + " | " + name + " | " + String.format("%.2f", price) + " | " + getCategory() + (recipe==null||recipe.getItems().isEmpty()?"":" | recipe:"+recipe.serialize());
    }
}
