package POS.Reporting.Utils;
import java.util.List;
import POS.Orders.Receipts.Receipt;


public class Calculator {

    public static double computeTotalSales(List<Receipt> receipts) {
        double total = 0;
        for (Receipt r : receipts) {
            total += r.getOrder().getTotalPayable();
        }
        return total;
    }
}
