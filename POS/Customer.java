package POS;


public class Customer {
    private IdStrategy id;
    private OrderList orders;


    public Customer (IdStrategy id) {
        this.id = id;
        this.orders = new OrderList();
    }

    public IdStrategy getId() {
        return id;
    }

    public OrderList getOrders() {
        return orders;
    }

    public void addItem(MenuItem item,  int quantity) {
        orders.addItem(item, quantity);
    }

    public void removeItem(int code) {
        orders.removeItem(code);
    }

    
}
