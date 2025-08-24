package POS;

public class Receipt {
    private static int counter = 1;  
    private int id;          
    private Order order;
    private double payment;
    private double change;
    private Cashier cashier;

    public Receipt(Order order, double payment, double change, Cashier cashier) {
        this.id = counter++;
        this.order = order;
        this.payment = payment;
        this.change = change;
        this.cashier = cashier;
    }

    public Order getOrder() {
        return order;
    }

    public int getId() {
        return id;
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

    public void displayReceipt() {
        Printer.printReceipt(this);
    }
}
