package pos.ui.tui;

import pos.ui.UI;
import pos.service.FileUtil;
import java.util.*;

public class SalesReportManagementTUI implements UI {
    private Scanner scanner = new Scanner(System.in);

    @Override 
    public void start() {
        while(true) {
            System.out.println("\n--- SALES REPORT ---");
            System.out.println("1) View All Sales Records");
            System.out.println("2) Daily Sales (today)");
            System.out.println("3) Monthly Sales");
            System.out.println("4) Yearly Sales");
            System.out.println("0) Back");
            System.out.print("Choose: "); 
            String c = scanner.nextLine().trim();
            
            if (c.equals("1")) viewAllSales();
            else if (c.equals("2")) viewDailySales();
            else if (c.equals("3")) viewMonthlySales();
            else if (c.equals("4")) viewYearlySales();
            else if (c.equals("0")) return;
            else System.out.println("Invalid");
        }
    }

    private void viewAllSales() {
        List<String> lines = FileUtil.readAllLines("resources/data/sales.txt");
        System.out.println("\n--- SALES RECORDS ---");
        for (String l : lines) System.out.println(l);
    }

    private void viewDailySales() {
        List<String> lines = FileUtil.readAllLines("resources/data/sales.txt");
        String today = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());
        double gross=0, discounts=0, net=0;
        int refundedItems = 0;
        double refundAmount = 0.0;

        for (String l : lines) {
            String[] p = l.split("\\|", -1);
            if (p.length < 7) continue;
            String dt = p[6];
            if (dt.startsWith(today) && (p.length < 8 || !"REFUND".equals(p[7]))) {
                double subtotal = Double.parseDouble(p[2]);
                double disc = Double.parseDouble(p[4]);
                double total = Double.parseDouble(p[5]);
                gross += subtotal;
                discounts += disc;
                net += total;
            }
            // handle explicit refund rows
            if (dt.startsWith(today) && p.length >= 8 && "REFUND".equals(p[7])) {
                double total = Double.parseDouble(p[5]);
                refundAmount += Math.abs(total);
                refundedItems += 1;
            }
        }

        System.out.println("Date: " + today);
        System.out.println("Gross: " + String.format("%.2f", gross));
        System.out.println("Discounts: " + String.format("%.2f", discounts));
        System.out.println("Net: " + String.format("%.2f", net));
        System.out.println("Total Refund Amount: " + String.format("%.2f", refundAmount));
        System.out.println("Total Refunded Items: " + refundedItems);
    }

    private void viewMonthlySales() {
        System.out.print("Enter month (1-12): "); 
        int month = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter year (e.g. 2025): "); 
        int year = Integer.parseInt(scanner.nextLine().trim());
        
        List<String> lines = FileUtil.readAllLines("resources/data/sales.txt");
        double gross=0, discounts=0, net=0, refundAmount=0.0;
        int refundedItems=0;

        for (String l : lines) {
            String[] p = l.split("\\|", -1);
            if (p.length < 7) continue;
            try {
                java.util.Date d = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(p[6]);
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTime(d);
                int m = cal.get(java.util.Calendar.MONTH) + 1;
                int y = cal.get(java.util.Calendar.YEAR);
                if (m==month && y==year) {
                    if (p.length < 8 || !"REFUND".equals(p[7])) {
                        double subtotal = Double.parseDouble(p[2]);
                        double disc = Double.parseDouble(p[4]);
                        double total = Double.parseDouble(p[5]);
                        gross += subtotal; discounts += disc; net += total;
                    } else {
                        double total = Double.parseDouble(p[5]);
                        refundAmount += Math.abs(total);
                        refundedItems += 1;
                    }
                }
            } catch(Exception ex) {
                // ignore invalid date rows
            }
        }

        System.out.println("Month: " + month + " Year: " + year);
        System.out.println("Gross: " + String.format("%.2f", gross));
        System.out.println("Discounts: " + String.format("%.2f", discounts));
        System.out.println("Net: " + String.format("%.2f", net));
        System.out.println("Total Refund Amount: " + String.format("%.2f", refundAmount));
        System.out.println("Total Refunded Items: " + refundedItems);
    }

    private void viewYearlySales() {
        System.out.print("Enter year (e.g. 2025): "); 
        int year = Integer.parseInt(scanner.nextLine().trim());
        
        List<String> lines = FileUtil.readAllLines("resources/data/sales.txt");
        double gross=0, discounts=0, net=0, refundAmount=0.0;
        int refundedItems=0;

        for (String l : lines) {
            String[] p = l.split("\\|", -1);
            if (p.length < 7) continue;
            try {
                java.util.Date d = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(p[6]);
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTime(d);
                int y = cal.get(java.util.Calendar.YEAR);
                if (y==year) {
                    if (p.length < 8 || !"REFUND".equals(p[7])) {
                        double subtotal = Double.parseDouble(p[2]);
                        double disc = Double.parseDouble(p[4]);
                        double total = Double.parseDouble(p[5]);
                        gross += subtotal; discounts += disc; net += total;
                    } else {
                        double total = Double.parseDouble(p[5]);
                        refundAmount += Math.abs(total);
                        refundedItems += 1;
                    }
                }
            } catch(Exception ex) {
                // ignore invalid date rows
            }
        }

        System.out.println("Year: " + year);
        System.out.println("Gross: " + String.format("%.2f", gross));
        System.out.println("Discounts: " + String.format("%.2f", discounts));
        System.out.println("Net: " + String.format("%.2f", net));
        System.out.println("Total Refund Amount: " + String.format("%.2f", refundAmount));
        System.out.println("Total Refunded Items: " + refundedItems);
    }
}
