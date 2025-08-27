package POS;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Receipt {
    private static int counter = 1;
    private int id;
    private Order order;
    private double payment;
    private double change;
    private Cashier cashier;
    private LocalDate date;   
    private RefundType refundType = RefundType.NONE;  // <-- new
    private double refundAmount = 0.0;

    public Receipt(Order order, double payment, double change, Cashier cashier) {
        this.id = counter++;
        this.order = order;
        this.payment = payment;
        this.change = change;
        this.cashier = cashier;
        this.date = LocalDate.now();
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

    public LocalDate getDate() {
        return date;
    }

    public RefundType getRefundType() {
        return refundType;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void processFullRefund() {
        this.refundType = RefundType.FULL;
        this.refundAmount = order.getTotalPayable();
    }

    public void processPartialRefund(double amount) {
        if (amount > 0 && amount <= order.getTotalPayable()) {
            this.refundType = RefundType.PARTIAL;
            this.refundAmount = amount;
        }
    }

    public void processReturn() {
        this.refundType = RefundType.RETURN;
        this.refundAmount = order.getTotalPayable();
    }

    @Override
    public String toString() {
        return "Receipt ID: " + id +
               "\nOrder ID: " + order.getId() +
               "\nPayment: " + payment +
               "\nChange: " + change +
               "\nRefund Status: " + refundType +
               (refundType != RefundType.NONE ? ("\nRefund Amount: " + refundAmount) : "") +
               "\nCashier: " + cashier.getName() +
               "\nDate: " + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void displayReceipt() {
        Printer.printReceipt(this);
    }
}
