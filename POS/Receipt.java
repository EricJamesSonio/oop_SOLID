package POS;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Receipt {
    private static int counter = 1;
    private int id;
    private Order order;
    private double payment;
    private double change;
    private Cashier cashier;
    private String date;  // Added date field

    public Receipt(Order order, double payment, double change, Cashier cashier) {
        this.id = counter++;
        this.order = order;
        this.payment = payment;
        this.change = change;
        this.cashier = cashier;

        // Set current date automatically in "yyyy-MM-dd" format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.date = LocalDateTime.now().format(formatter);
    }

    public int getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public double getPayment() {
        return payment;
    }

    public double getChange() {
        return change;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public String getDate() {
        return date;
    }

    public void displayReceipt() {
        Printer.printReceipt(this);
    }
}
