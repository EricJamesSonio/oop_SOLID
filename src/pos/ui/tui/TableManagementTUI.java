package pos.ui.tui;

import pos.ui.UI;
import pos.service.TableService;
import pos.model.Table;

import java.util.List;
import java.util.Scanner;

public class TableManagementTUI implements UI {
    private final TableService tableService;
    private final Scanner scanner = new Scanner(System.in);

    public TableManagementTUI(TableService tableService) {
        this.tableService = tableService;
    }

    @Override
    public void start() {
        while (true) {
            System.out.println("\n--- TABLE MANAGEMENT ---");
            System.out.println("1) View Tables");
            System.out.println("2) Assign Customer to Table");
            System.out.println("3) Clean Table");
            System.out.println("4) Add Table");
            System.out.println("5) Remove Table");
            System.out.println("0) Back");
            System.out.print("Choose: ");
            String c = scanner.nextLine().trim();

            switch (c) {
                case "1": viewTables(); break;
                case "2": assignCustomerToTable(); break;
                case "3": cleanTable(); break;
                case "4": addTable(); break;
                case "5": removeTable(); break;
                case "0": return;
                default: System.out.println("Invalid"); break;
            }
        }
    }

    private void viewTables() {
        System.out.println("\n--- TABLES ---");
        List<Table> tables = tableService.all();
        if (tables.isEmpty()) {
            System.out.println("No tables available.");
        } else {
            for (Table t : tables) {
                System.out.println(t);
            }
        }
    }

    private void assignCustomerToTable() {
        viewTables();
        Table t = promptTable("Table id: ");
        if (t == null) return;

        if (!"AVAILABLE".equalsIgnoreCase(t.getStatus())) {
            System.out.println("Table not available");
            return;
        }

        int pc = promptInt("Number of people: ");
        if (pc > t.getCapacity()) {
            System.out.println("Exceeds capacity");
            return;
        }

        t.setStatus("OCCUPIED");
        t.setCustomerCount(pc);
        tableService.saveAll();
        System.out.println("Assigned.");
    }

    private void cleanTable() {
        viewTables();
        Table t = promptTable("Table id to clean: ");
        if (t == null) return;

        if (!"DIRTY".equalsIgnoreCase(t.getStatus())) {
            System.out.println("Not dirty");
            return;
        }

        t.setStatus("AVAILABLE");
        t.setCustomerCount(0);
        tableService.saveAll();
        System.out.println("Cleaned.");
    }

    private void addTable() {
        int cap = promptInt("Capacity: ");
        tableService.add(cap);
        System.out.println("Added table.");
    }

    private void removeTable() {
        viewTables();
        int id = promptInt("Enter table id to remove: ");
        tableService.remove(id);
        System.out.println("Removed table if existed.");
    }

    // === Helpers ===
    private Table promptTable(String prompt) {
        int id = promptInt(prompt);
        Table t = tableService.findById(id);
        if (t == null) {
            System.out.println("Not found");
        }
        return t;
    }

    private int promptInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, try again.");
            }
        }
    }
}
