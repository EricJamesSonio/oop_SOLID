package POS.Orders.Management;

import POS.Orders.Receipts.ReceiptRepository;
import POS.Orders.Receipts.Receipt;
import POS.Orders.Models.Order;
import POS.Orders.Models.OrderItem;
import POS.Orders.Models.OrderList;
import POS.Orders.Base.OrderStatus;
import POS.Orders.Base.CustomerType;
import POS.Employee.Staff.Cashier;
import POS.Orders.Services.DiscountCalculator;

import java.util.*;

public class OrderManagement {
    private final List<Order> orders;
    private final OrderViewer viewer;
    private static int nextId = 1;
    private final ReceiptRepository receiptRepo;
    private static final OrderManagement instance = new OrderManagement();

    private OrderManagement() {
        this.orders = new ArrayList<>();
        this.viewer = new OrderViewer(this);
        this.receiptRepo = new ReceiptRepository();
    }

    public static OrderManagement getInstance() {
        return instance;
    }

    // --- Processing ---
    public boolean processOrder(int orderId, double payment, Cashier cashier) {
        Order existing = findOrder(orderId);
        if (existing == null) return false;
        if (payment < existing.getTotalPayable()) return false;

        cashier.processOrder(existing, payment);
        existing.setStatus(OrderStatus.COMPLETED);
        return true;
    }

    // --- Add order directly with CustomerType and OrderList ---
    public boolean addOrder(CustomerType customerType, OrderList orderList, DiscountCalculator discountCalculator) {
        if (orderList.getItems().isEmpty()) return false;

        int id = nextId++;
        Order order = new Order(customerType, id, discountCalculator);

        // Add items from OrderList to Order
        for (Map.Entry<OrderItem, Integer> entry : orderList.getItems().entrySet()) {
            order.addItem(entry.getKey().getItem(), entry.getValue());
        }

        orders.add(order);
        return true;
    }

    public boolean removeOrder(int id) {
        Order existing = findOrder(id);
        if (existing == null) return false;

        orders.remove(existing);
        return true;
    }

    public boolean updateOrderStatus(int id, OrderStatus newStatus) {
        Order order = findOrder(id);
        if (order == null) return false;

        order.setStatus(newStatus);
        return true;
    }

    public boolean fullRefundOrder(int id) {
        Order existing = findOrder(id);
        if (existing == null) return false;

        existing.setStatus(OrderStatus.REFUNDED);
        existing.setTotalsToZero();

        Receipt receipt = receiptRepo.findReceiptById(id);
        if (receipt != null) receipt.processFullRefund();

        return true;
    }

    public boolean partialRefundOrder(int id, double amount) {
        Order existing = findOrder(id);
        if (existing == null) return false;
        if (amount <= 0 || amount > existing.getTotalPayable()) return false;

        Receipt receipt = receiptRepo.findReceiptById(id);
        if (receipt != null) receipt.processPartialRefund(amount);

        return true;
    }

    public boolean returnOrder(int id) {
        Order existing = findOrder(id);
        if (existing == null) return false;

        existing.setStatus(OrderStatus.REFUNDED);
        existing.setTotalsToZero();

        Receipt receipt = receiptRepo.findReceiptById(id);
        if (receipt != null) receipt.processReturn();

        return true;
    }

    public Order findOrder(int id) {
        for (Order order : orders) {
            if (order.getId() == id) return order;
        }
        return null;
    }

    public void displayOrders() {
        viewer.displayOrders();
    }

    public List<Order> getOrders() { return orders; }
    public ReceiptRepository getReceiptRepo() { return receiptRepo; }
}

// ---------------- OrderViewer ----------------
class OrderViewer {
    private final OrderManagement management;

    public OrderViewer(OrderManagement management) {
        this.management = management;
    }

    public void displayOrders() {
        for (Order o : management.getOrders()) {
            System.out.println(o);
        }
    }

    public OrderManagement getManagement() { return management; }
}
