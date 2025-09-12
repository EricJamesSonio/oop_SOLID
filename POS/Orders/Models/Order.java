package POS.Orders.Models;

import POS.Orders.Base.CustomerType;
import POS.Orders.Base.OrderStatus;
import POS.Menu.Base.MenuItem;
import POS.Orders.Services.DiscountCalculator;
import POS.Reporting.Printer.Printer;

import java.util.HashMap;

public class Order {
    private final OrderList orderList;
    private final CustomerType customerType;
    private double subTotal;
    private double discountAmount;
    private double totalPayable;
    private OrderStatus status;
    private final int id;

    private final DiscountCalculator discountCalculator;

    public Order(CustomerType customerType, int id, DiscountCalculator discountCalculator) {
        this.id = id;
        this.customerType = customerType;
        this.orderList = new OrderList();
        this.subTotal = 0;
        this.discountAmount = 0;
        this.totalPayable = 0;
        this.status = OrderStatus.PENDING;
        this.discountCalculator = discountCalculator;
    }

    // --- Item management ---
    public void addItem(MenuItem item, int quantity) {
        orderList.addItem(item, quantity);
        recalculateTotals();
    }

    public void removeItem(int code) {
        orderList.removeItem(code);
        recalculateTotals();
    }

    public void updateQuantity(int code, int quantity) {
        orderList.updateQuantity(code, quantity);
        recalculateTotals();
    }


    public double computeSubTotal() {
        double total = 0;
        for (OrderItem item : orderList.getItems().keySet()) {
            total += item.getPrice() * orderList.getItems().get(item);
        }
        this.subTotal = total;
        return subTotal;
    }

    public double computeDiscount() {
        this.discountAmount = discountCalculator.calculateDiscount(subTotal);
        return discountAmount;
    }

    public double computeTotalPayable() {
        this.totalPayable = subTotal - discountAmount;
        return totalPayable;
    }

    public void recalculateTotals() {
        computeSubTotal();
        computeDiscount();
        computeTotalPayable();
    }


    public void setTotalsToZero() {
        this.subTotal = 0;
        this.discountAmount = 0;
        this.totalPayable = 0;
    }

    public HashMap<OrderItem, Integer> getItems() { return orderList.getItems(); }
    public double getSubTotal() { return subTotal; }
    public double getDiscountAmount() { return discountAmount; }
    public double getTotalPayable() { return totalPayable; }
    public OrderStatus getStatus() { return status; }
    public int getId() { return id; }
    public CustomerType getCustomerType() { return customerType; }

    public void setStatus(OrderStatus newStatus) { this.status = newStatus; }

    public void displayItems() { Printer.printOrder(this); }
}
