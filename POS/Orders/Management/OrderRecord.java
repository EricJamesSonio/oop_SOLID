package POS.Orders.Management;
import POS.Orders.Receipts.Receipt;

import java.util.ArrayList;
import java.util.List;

public class OrderRecord {
    private List <Receipt> receipts;

    public OrderRecord () {
        this.receipts = new ArrayList<>();
    }

    public void addReceipt(Receipt receipt) {
        Receipt existing = findReceipt(receipt.getId());

        if (existing == null) {
            receipts.add(receipt);
        } else {
            return;
        }
    }

    public Receipt findReceipt (int id) {
        for (Receipt r : receipts) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    public List<Receipt> getOrders() {
        return receipts;
    }
}
