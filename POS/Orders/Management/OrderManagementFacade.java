package POS.Orders.Management;

import POS.Orders.Base.CustomerType;
import POS.Orders.Base.OrderStatus;
import POS.Orders.Models.Order;
import POS.Orders.Models.OrderItem;
import POS.Orders.Models.OrderList;
import POS.Orders.Services.DiscountCalculator;
import POS.Orders.Management.Interfaces.IOrderRepository;
import POS.Orders.Management.Interfaces.IOrderProcessor;
import POS.Orders.Management.Interfaces.IOrderRefundService;
import POS.Orders.Management.Interfaces.IOrderViewer;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderManagementFacade {
    private final IOrderRepository repository;
    private final IOrderProcessor processor;
    private final IOrderRefundService refundService;
    private final IOrderViewer viewer;

    private final AtomicInteger nextId = new AtomicInteger(1);

    // --- Constructor injection ---
    public OrderManagementFacade(IOrderRepository repository,
                                 IOrderProcessor processor,
                                 IOrderRefundService refundService,
                                 IOrderViewer viewer) {
        this.repository = repository;
        this.processor = processor;
        this.refundService = refundService;
        this.viewer = viewer;
    }

    // --- Add order ---
    public boolean addOrder(CustomerType customerType, OrderList orderList, DiscountCalculator discountCalculator) {
        if (orderList.getItems().isEmpty()) return false;

        int id = nextId.getAndIncrement();
        Order order = new Order(customerType, id, discountCalculator);

        for (Map.Entry<OrderItem, Integer> entry : orderList.getItems().entrySet()) {
            order.addItem(entry.getKey().getItem(), entry.getValue());
        }

        repository.addOrder(order);
        return true;
    }

    // --- Other operations ---
    public boolean removeOrder(int id) {
        return repository.removeOrder(id);
    }

    public Order findOrder(int id) {
        return repository.findOrder(id);
    }

    public boolean processOrder(int orderId, double payment) {
        Order order = repository.findOrder(orderId);
        return processor.processOrder(order, payment);
    }

    public boolean updateOrderStatus(int id, OrderStatus newStatus) {
        Order order = repository.findOrder(id);
        return processor.updateStatus(order, newStatus);
    }

    public void fullRefund(int id) {
        Order order = repository.findOrder(id);
        refundService.fullRefund(order);
    }

    public void partialRefund(int id, double amount) {
        Order order = repository.findOrder(id);
        refundService.partialRefund(order, amount);
    }

    public void returnOrder(int id) {
        Order order = repository.findOrder(id);
        refundService.returnOrder(order);
    }

    public void displayOrders() {
        viewer.displayOrders(repository.getOrders());
    }
}
