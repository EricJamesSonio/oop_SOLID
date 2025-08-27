package POS;

public class OrderItem {
    private MenuItem item;

    public OrderItem(MenuItem item) {
        this.item = item;
    }    

    public String getDetails() {
        return String.format("Name : %s, Price : %.2f" , item.getName(), item.getPrice());
    }

    public String getName() {
        return item.getName();
    }

    public int getCode() {
        return item.getCode();
    }

    public double getPrice() {
        return item.getPrice();
    }

    public MenuItem getItem() {
        return item;
    }
}
