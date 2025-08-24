package POS;

import java.util.ArrayList;
import java.util.List;

public class SalesReport {

    private ReceiptRepository receiptRepository;

    public SalesReport() {
        this.receiptRepository = ReceiptRepository.getInstance();
    }

    public List<Receipt> getReceiptsByCashier(Cashier cashier) {
        List<Receipt> result = new ArrayList<>();
        for (Receipt r : receiptRepository.getAllReceipts()) {
            if (r.getCashier().equals(cashier)) {
                result.add(r);
            }
        }
        return result;
    }

    public List<Receipt> getReceiptsByDate(String date) {
        List<Receipt> result = new ArrayList<>();
        for (Receipt r : receiptRepository.getAllReceipts()) {
            if (r.getDate().equals(date)) {
                result.add(r);
            }
        }
        return result;
    }

    public List<Receipt> getReceiptsByMonth(String month) {
        List<Receipt> result = new ArrayList<>();
        for (Receipt r : receiptRepository.getAllReceipts()) {
            if (r.getDate().startsWith(month)) {
                result.add(r);
            }
        }
        return result;
    }

    public List<Receipt> getReceiptsByYear(String year) {
        List<Receipt> result = new ArrayList<>();
        for (Receipt r : receiptRepository.getAllReceipts()) {
            if (r.getDate().startsWith(year)) { 
                result.add(r);
            }
        }
        return result;
    }

    public void displayReport(List<Receipt> receipts) {
        double total = Calculator.computeTotalSales(receipts);
        Printer.printSalesReport(receipts, total);
    }

}

// just a helper for the sales report since i'm wanting to have SRP
class Calculator {

    public static double computeTotalSales(List<Receipt> receipts) {
        double total = 0;
        for (Receipt r : receipts) {
            total += r.getOrder().getTotalPayable();
        }
        return total;
    }
}
