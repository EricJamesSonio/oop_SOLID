package POS;

public abstract class MenuItem {
    private String name;
    private int code;
    private double price;

    public MenuItem (String name, int code, double price) {
        this.name = name;
        this.code = code;
        this.price = price;
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

    public abstract String getDetails();

    @Override
    public String toString() {
        return String.format("Name : %s , Code : %d , Price : %.2f ", name, code, price);
    }
}





