
package pos.model;

public class Ingredient {
    private int id;
    private String name;
    private int stock; // integer units (grams / pieces / ml)
    private String unit;
    private int threshold;

    public Ingredient(int id, String name, int stock, String unit, int threshold) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.unit = unit;
        this.threshold = threshold;
    }
    public int getId(){ return id; }
    public String getName(){ return name; }
    public int getStock(){ return stock; }
    public void setStock(int s){ this.stock = s; }
    public String getUnit(){ return unit; }
    public int getThreshold(){ return threshold; }
    @Override
    public String toString(){
        return id + " | " + name + " | " + stock + " " + unit + " | threshold:" + threshold;
    }
}
