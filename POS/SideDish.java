package POS;

public class SideDish extends MenuItem {
    public SideDish (String name, int code, double price) {
        super(name, code, price);
    }

    @Override
    public String getDetails() {
        return "Name : " + getName() + " Code : " + getCode() + " Price : " + getPrice() ;
    }    
}