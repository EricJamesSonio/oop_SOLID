package POS.Reporting.Reports;
import POS.Orders.Receipts.ReceiptRepository;
import POS.Orders.Receipts.Receipt;
import POS.Reporting.Utils.Calculator;
import POS.Reporting.Printer.Printer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalesReport {

    private ReceiptRepository receiptRepository;

    public SalesReport(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    public List<Receipt> getReceiptsByDate(LocalDate date) {
        List<Receipt> result = new ArrayList<>();
        for (Receipt r : receiptRepository.getAllReceipts()) {
            if (r.getDate().isEqual(date)) {
                result.add(r);
            }
        }
        return result;
    }

    public List<Receipt> getReceiptsByMonth(int year, int month) {
        List<Receipt> result = new ArrayList<>();
        for (Receipt r : receiptRepository.getAllReceipts()) {
            if (r.getDate().getYear() == year && r.getDate().getMonthValue() == month) {
                result.add(r);
            }
        }
        return result;
    }

    public List<Receipt> getReceiptsByYear(int year) {
        List<Receipt> result = new ArrayList<>();
        for (Receipt r : receiptRepository.getAllReceipts()) {
            if (r.getDate().getYear() == year) {
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



