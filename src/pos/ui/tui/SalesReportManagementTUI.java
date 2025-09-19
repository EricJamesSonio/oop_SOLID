package pos.ui.tui;

import pos.ui.UI;
import pos.service.FileUtil;

import java.util.List;
import java.util.Scanner;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.function.Predicate;

public class SalesReportManagementTUI implements UI {
    private final Scanner scanner = new Scanner(System.in);
    private static final String SALES_FILE = "resources/data/sales.txt";
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat DATE_ONLY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void start() {
        while (true) {
            System.out.println("\n--- SALES REPORT ---");
            System.out.println("1) View All Sales Records");
            System.out.println("2) Daily Sales (today)");
            System.out.println("3) Monthly Sales");
            System.out.println("4) Yearly Sales");
            System.out.println("0) Back");
            System.out.print("Choose: ");
            String c = scanner.nextLine().trim();

            switch (c) {
                case "1": viewAllSales(); break;
                case "2": viewDailySales(); break;
                case "3": viewMonthlySales(); break;
                case "4": viewYearlySales(); break;
                case "0": return;
                default: System.out.println("Invalid"); break;
            }
        }
    }

    private void viewAllSales() {
        List<String> lines = FileUtil.readAllLines(SALES_FILE);
        System.out.println("\n--- SALES RECORDS ---");
        for (String l : lines) System.out.println(l);
    }

    private void viewDailySales() {
        String today = DATE_ONLY_FORMAT.format(new Date());
        summarizeSales(d -> DATE_ONLY_FORMAT.format(d).equals(today),
                "Date: " + today);
    }

    private void viewMonthlySales() {
        try {
            System.out.print("Enter month (1-12): ");
            int month = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter year (e.g. 2025): ");
            int year = Integer.parseInt(scanner.nextLine().trim());

            summarizeSales(d -> {
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                return (cal.get(Calendar.MONTH) + 1 == month && cal.get(Calendar.YEAR) == year);
            }, "Month: " + month + " Year: " + year);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numeric values.");
        }
    }

    private void viewYearlySales() {
        try {
            System.out.print("Enter year (e.g. 2025): ");
            int year = Integer.parseInt(scanner.nextLine().trim());

            summarizeSales(d -> {
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                return cal.get(Calendar.YEAR) == year;
            }, "Year: " + year);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid year.");
        }
    }

    private void summarizeSales(Predicate<Date> filter, String header) {
        List<String> lines = FileUtil.readAllLines(SALES_FILE);
        double gross = 0, discounts = 0, net = 0, refundAmount = 0.0;
        int refundedItems = 0;

        for (String l : lines) {
            String[] p = l.split("\\|", -1);
            if (p.length < 7) continue;

            try {
                Date d;
                // try parsing full datetime first
                try {
                    d = DATE_TIME_FORMAT.parse(p[6]);
                } catch (ParseException ex) {
                    // fallback if only date is stored
                    d = DATE_ONLY_FORMAT.parse(p[6]);
                }

                if (filter.test(d)) {
                    if (p.length >= 8 && "REFUND".equals(p[7])) {
                        refundAmount += Math.abs(Double.parseDouble(p[5]));
                        refundedItems++;
                    } else {
                        gross += Double.parseDouble(p[2]);
                        discounts += Double.parseDouble(p[4]);
                        net += Double.parseDouble(p[5]);
                    }
                }
            } catch (Exception ignored) {}
        }

        System.out.println(header);
        System.out.println("Gross: " + String.format("%.2f", gross));
        System.out.println("Discounts: " + String.format("%.2f", discounts));
        System.out.println("Net: " + String.format("%.2f", net));
        System.out.println("Total Refund Amount: " + String.format("%.2f", refundAmount));
        System.out.println("Total Refunded Items: " + refundedItems);
    }
}
