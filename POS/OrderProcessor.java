package POS;

public class OrderProcessor {
    private OrderRecord records;

    public OrderProcessor () {
        this.records = new OrderRecord();
    }

    public void processOrder(Customer customer, double payment, Cashier cashier) {
        Order order = new Order(customer);

        if (order.getTotalPayable() > payment ) {
            System.out.println("Payment not Enough! ");
            return;
        }

        double change = payment - order.getTotalPayable();

        Receipt receipt = new Receipt(order, payment, change, cashier);
        receipt.displayReceipt();
        records.addReceipt(receipt);

        System.out.println("Payment Succesfully! ");
    }
    
    public OrderRecord getRecords() {
        return records;
    }
}
