package POS;

public class RefundService {
    public static void refundOrder(Order order) {
        order.setStatus(OrderStatus.REFUNDED);
        order.setTotalsToZero(); // method inside Order
    }

    public static void partialRefund(Order order, OrderItem item, int quantity) {
        // reduce or remove item from the order
        int currentQty = order.getItems().getOrDefault(item, 0);
        if (currentQty > quantity) {
            order.getItems().put(item, currentQty - quantity);
        } else {
            order.getItems().remove(item);
        }
        // re-compute totals after refund
        order.recalculateTotals();
    }
}
