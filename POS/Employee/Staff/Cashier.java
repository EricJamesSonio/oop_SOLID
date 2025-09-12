package POS.Employee.Staff;
import POS.Employee.Base.Employee;
import POS.Orders.Management.OrderProcessor;
import POS.Orders.Management.OrderRecord;
import POS.Orders.Models.Order;;



public class Cashier extends Employee{
    private OrderProcessor processor;

    public Cashier(String name, int id) {
        super(name, id);
        this.processor = new OrderProcessor();
    }

    public void processOrder(Order order, double payment) {
        processor.processOrder(order, payment, this);
    }

    public OrderRecord getRecords() {
        return processor.getRecords();
    }

    public OrderProcessor getOrderProcessor() {
        return processor;
    }

    @Override
    public String getDetails() {
        return String.format("Cashier Name : %s , Id : %d ", getName() ,getId());
    }
}
