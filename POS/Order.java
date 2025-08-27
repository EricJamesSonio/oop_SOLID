package POS;

import java.util.HashMap;

public class Order {
    private Customer customer;
    private double subTotal;
    private double discountAmount;
    private double totalPayable;
    private OrderStatus status;
    private int id;
    

    public Order(Customer customer, int id) {
        this.id = id;
        this.customer = customer;  
        this.subTotal = computeSubTotal();
        this.discountAmount = computeDiscount();
        this.totalPayable = computeTotalPayable();
        this.status = OrderStatus.PENDING;
    }

    public double computeSubTotal() {
        double total = 0;
        for (OrderItem item : customer.getOrders().getItems().keySet()) {
            total += item.getPrice() * customer.getOrders().getItems().get(item);
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

    public void setStatus(OrderStatus newStatus) {
        status = newStatus;
    }


    public void displayItems() {
        Printer.printOrder(this);
    }

    public Customer getCustomer() {
        return customer;
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

    public OrderStatus getStatus() {
        return status;
    }

    public HashMap<OrderItem, Integer> getItems() {
        return customer.getOrders().getItems();
    }

    public void setTotalsToZero() {
        this.subTotal = 0;
        this.discountAmount = 0;
        this.totalPayable = 0;
    }

    public void recalculateTotals() {
        computeSubTotal();
        computeDiscount();
        computeTotalPayable();
    }

    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order Details:\n");
        sb.append("Customer: ").append(customer != null ? customer.toString() : "No Customer").append("\n");
        sb.append("Items:\n");

        for (OrderItem item : getItems().keySet()) {
            int quantity = getItems().get(item);
            sb.append(" - ").append(item.getName())
              .append(" x").append(quantity)
              .append(" @ ").append(item.getPrice())
              .append(" = ").append(item.getPrice() * quantity)
              .append("\n");
        }

        sb.append("Subtotal: ").append(subTotal).append("\n");
        sb.append("Discount: ").append(discountAmount).append("\n");
        sb.append("Total Payable: ").append(totalPayable).append("\n");
        sb.append("Status: ").append(status).append("\n");

        return sb.toString();
    }
}
