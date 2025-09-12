package POS.Table.Models;
import POS.Table.Base.TableStatus;
import POS.Orders.Base.CustomerType;
import POS.Orders.Models.OrderList;

public class Table {
    private CustomerType customerType;   // replaces Customer
    private OrderList orderList;         // store orders for this table
    private int capacity;
    private TableStatus status;
    private int id;

    public Table(int id, int capacity) {
        this.capacity = capacity;
        this.status = TableStatus.AVAILABLE;
        this.id = id;
        this.customerType = null;
        this.orderList = new OrderList();
    }

    // Assigns a table to a customer type and initializes their orders
    public boolean assignCustomer(CustomerType customerType, int personCount) {
        if (personCount > capacity) {
            return false;
        } else {
            this.customerType = customerType;
            this.orderList = new OrderList(); // fresh orders for this session
            this.status = TableStatus.OCCUPIED;
            return true;
        }
    }

    // Clears the table
    public void clearTable() {
        this.customerType = null;
        this.orderList = new OrderList();
        this.status = TableStatus.AVAILABLE;
    }

    // --- Getters ---
    public CustomerType getCustomerType() {
        return customerType;
    }

    public OrderList getCustomerOrder() {
        return orderList;
    }

    public int getCapacity() {
        return capacity;
    }

    public TableStatus getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }
}
