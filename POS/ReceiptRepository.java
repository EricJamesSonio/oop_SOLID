package POS;

import java.util.ArrayList;
import java.util.List;

public class ReceiptRepository { 
    private static final ReceiptRepository instance = new ReceiptRepository();
    private final List<Receipt> receipts;


    private ReceiptRepository() {
        this.receipts = new ArrayList<>();
    }

    public static ReceiptRepository getInstance() {
        return instance;
    }

    public void addReceipt(Receipt receipt) {
        if (findReceiptById(receipt.getId()) == null) {
            receipts.add(receipt);
        }
    }


    public List<Receipt> getAllReceipts() {
        return new ArrayList<>(receipts); 
    }

    public Receipt findReceiptById(int id) {
        for (Receipt r : receipts) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }
}
