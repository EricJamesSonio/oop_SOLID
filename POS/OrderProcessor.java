package POS;

public class OrderProcessor {
    private OrderRecord records;

    public OrderProcessor () {
        this.records = new OrderRecord();
    }

    public void processOrder(Customer customer, double payment, Cashier cashier) {
        Order order = new Order(customer);

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
