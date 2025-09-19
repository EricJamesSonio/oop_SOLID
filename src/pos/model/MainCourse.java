
package pos.model;

public class MainCourse extends MenuItem {
    public MainCourse(int id, String name, double price, Recipe recipe){
        super(id,name,price,recipe);
    }
    @Override public String getCategory(){ return "MainCourse"; }
}
