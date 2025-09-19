
package pos.model;

public class SideDish extends MenuItem {
    public SideDish(int id, String name, double price, Recipe recipe){
        super(id,name,price,recipe);
    }
    @Override public String getCategory(){ return "SideDish"; }
}
