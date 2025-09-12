package POS.Orders.Management;

import POS.Orders.Management.Interfaces.IOrderRepository;
import POS.Orders.Management.Interfaces.IOrderProcessor;
import POS.Orders.Management.Interfaces.IOrderRefundService;
import POS.Orders.Management.Interfaces.IOrderViewer;
import POS.Orders.Receipts.ReceiptRepository;

public class OrderFactoryPlant {

    // Default: creates a fresh empty repository
    public static OrderManagementFacade createOrderManagementFacade() {
        return createOrderManagementFacade(new OrderRepository());
    }

    // Overloaded: accepts a preloaded repository (e.g., from JSON)
    public static OrderManagementFacade createOrderManagementFacade(IOrderRepository repository) {
        // Repositories
        ReceiptRepository receiptRepo = new ReceiptRepository();

        // Services
        IOrderProcessor processor = new OrderProcessor();
        IOrderRefundService refundService = new RefundService(receiptRepo);

        // Viewer
        IOrderViewer viewer = new OrderViewer();

        // Facade
        return new OrderManagementFacade(repository, processor, refundService, viewer);
    }
}
