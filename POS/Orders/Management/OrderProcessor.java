package POS.Orders.Management;
import POS.Orders.Models.Order;
import POS.Reporting.Printer.Printer;
import POS.Employee.Staff.Cashier;
import POS.Orders.Receipts.Receipt;

public class OrderProcessor {
    private OrderRecord records;

    public OrderProcessor () {
        this.records = new OrderRecord();
    }

    public void processOrder(Order order, double payment, Cashier cashier) {
        
        if (order.getTotalPayable() > payment ) {
            Printer.printError("Payment not Enough!");
            return;
        }

        double change = payment - order.getTotalPayable();

        Receipt receipt = new Receipt(order, payment, change, cashier);
        Printer.printReceipt(receipt);   // i Use Printer here instead of putting prints 
        records.addReceipt(receipt);

        Printer.printMessage("Payment Successfully!");
    }
    
    public OrderRecord getRecords() {
        return records;
    }
}
