package POS.Orders.Management;

import POS.Orders.Management.Interfaces.IOrderRefundService;
import POS.Orders.Models.Order;
import POS.Orders.Receipts.ReceiptRepository;
import POS.Orders.Base.OrderStatus;

public class RefundService implements IOrderRefundService {
    private final ReceiptRepository receiptRepo;

    public RefundService(ReceiptRepository receiptRepo) {
        this.receiptRepo = receiptRepo;
    }

    @Override
    public void fullRefund(Order order) {
        if (order == null) return;
        order.setStatus(OrderStatus.REFUNDED);
        order.setTotalsToZero();
        var receipt = receiptRepo.findReceiptById(order.getId());
        if (receipt != null) receipt.processFullRefund();
    }

    @Override
    public void partialRefund(Order order, double amount) {
        if (order == null || amount <= 0 || amount > order.getTotalPayable()) return;
        var receipt = receiptRepo.findReceiptById(order.getId());
        if (receipt != null) receipt.processPartialRefund(amount);
    }

    @Override
    public void returnOrder(Order order) {
        if (order == null) return;
        order.setStatus(OrderStatus.REFUNDED);
        order.setTotalsToZero();
        var receipt = receiptRepo.findReceiptById(order.getId());
        if (receipt != null) receipt.processReturn();
    }
}
