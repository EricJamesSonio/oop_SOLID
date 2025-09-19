
package pos.model;

public class Drink extends MenuItem {
    public Drink(int id, String name, double price){
        super(id,name,price,null);
    }
    @Override public String getCategory(){ return "Drink"; }
}
