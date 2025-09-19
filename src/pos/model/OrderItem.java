
package pos.model;

public class OrderItem {
    private int menuItemId;
    private String menuItemName;
    private int quantity;
    private double price;

    public OrderItem(int menuItemId, String menuItemName, int quantity, double price){
        this.menuItemId = menuItemId; this.menuItemName = menuItemName; this.quantity = quantity; this.price = price;
    }
    public int getMenuItemId(){ return menuItemId; }
    public String getMenuItemName(){ return menuItemName; }
    public int getQuantity(){ return quantity; }
    public double getPrice(){ return price; }
    public double getSubtotal(){ return price * quantity; }
    @Override public String toString(){ return menuItemId+"|"+menuItemName+"|"+quantity+"|"+String.format("%.2f",price); }
    public static OrderItem parse(String s){
        // format menuItemId|name|qty|price
        String[] p = s.split("\\|", -1);
        try {
            int id = Integer.parseInt(p[0]);
            String name = p[1];
            int q = Integer.parseInt(p[2]);
            double pr = Double.parseDouble(p[3]);
            return new OrderItem(id,name,q,pr);
        } catch(Exception ex){ return null; }
    }
}
