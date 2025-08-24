package POS;

public class Order {
    private Customer customer;
    private double subTotal;
    private double discountAmount;
    private double totalPayable;

    public Order(Customer customer) {
        this.customer = customer;  
        this.subTotal = computeSubTotal();
        this.discountAmount = computeDiscount();
        this.totalPayable = computeTotalPayable();
    }

    public double computeSubTotal() {
        double total = 0;
        for (MenuItem item : customer.getOrders().getItems().keySet()) {
            total += item.getPrice();
        }
        this.subTotal = total;
        return subTotal;
    }

    public double computeDiscount() {
        double totalDiscount = 0.0;

        if (customer.getId().getIsPWD()) {
            PWDDiscount pwd = new PWDDiscount("PWD Discount", 0.12);
            totalDiscount += pwd.applyDiscount(subTotal);
        }

        if (customer.getId().getIsSenior()) {
            SeniorDiscount senior = new SeniorDiscount("Senior Discount", 0.10);
            totalDiscount += senior.applyDiscount(subTotal);
        }

        this.discountAmount = totalDiscount;
        return discountAmount;
    }

    public double computeTotalPayable() {
        this.totalPayable = subTotal - discountAmount;
        return totalPayable;
    }

    public void displayItems() {
        for (MenuItem item : customer.getOrders().getItems().keySet()) {
            System.out.println(item.getDetails());
        }
    }

    public double getSubTotal() {
        return subTotal;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getTotalPayable() {
        return totalPayable;
    }
}
