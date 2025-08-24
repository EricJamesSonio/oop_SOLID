package POS;

public class Cashier extends Employee{
    private OrderProcessor processor;

    public Cashier(String name, int id) {
        super(name, id);
        this.processor = new OrderProcessor();
    }

    public void processOrder(Customer customer, double payment) {
        processor.processOrder(customer, payment, this);
    }

    public OrderRecord getRecords() {
        return processor.getRecords();
    }

    public OrderProcessor getOrderProcessor() {
        return processor;
    }
}
