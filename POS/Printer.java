package POS;

import java.util.List;

public class Printer {

    public static void printReceipt(Receipt receipt) {
        System.out.println("======================================");
        System.out.println("              RECEIPT #" + receipt.getId() + "           ");
        System.out.println("======================================");
        System.out.println("Cashier: " + receipt.getCashier().getName() +
                           " Id : " + receipt.getCashier().getId());
        System.out.println("--------------------------------------");
        System.out.println("Items Ordered:");
        receipt.getOrder().displayItems(); 
        System.out.println("--------------------------------------");
        System.out.printf("SubTotal:      %.2f%n", receipt.getOrder().getSubTotal());
        System.out.printf("Discounts:    -%.2f%n", receipt.getOrder().getDiscountAmount());
        System.out.printf("Total Payable: %.2f%n", receipt.getOrder().getTotalPayable());
        System.out.printf("Payment:       %.2f%n", receipt.getPayment());
        System.out.printf("Change:        %.2f%n", receipt.getChange());
        System.out.println("======================================");
        System.out.println("         THANK YOU, COME AGAIN!       ");
        System.out.println("======================================");
    }

    public static void printSalesReport(List<Receipt> receipts, double total) {
        System.out.println("===== SALES REPORT =====");
        for (Receipt r : receipts) {
            System.out.println("Receipt #" + r.getId() +
                               " | Cashier: " + r.getCashier().getName() + " Id : " + r.getCashier().getId() + 
                               " | Total: " + r.getOrder().getTotalPayable());
        }
        System.out.println("------------------------");
        System.out.println("Total Sales: " + total);
        System.out.println("========================");
    }


    public static void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    public static void printMessage(String message) {
        System.out.println("[INFO] " + message);
    }
}
