package POS;

public class Table {
    private Customer customer;
    private int capacity;
    private TableStatus status;
    private int id;

    public Table (int id, int capacity) {
        this.capacity = capacity;
        this.status = TableStatus.AVAILABLE;
        this.id = id;
        this.customer = null;
    }

    public boolean assignCustomer(Customer customer, int personCount) {
        if (personCount > capacity) {
            return false;
        } else  {
            this.customer = customer;
            this.status = TableStatus.OCCUPIED;
            return true;
        }
    } 

    public void clearTable() {
        this.customer = null;
        this.status = TableStatus.AVAILABLE;
    }


    public Customer getCustomer() {
        return customer;
    }

    public OrderList getCustomerOrder() {
        return (customer != null) ? customer.getOrders() : new OrderList(); // returns empty list if no customer
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
