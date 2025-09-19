package pos.ui.tui;

import pos.ui.UI;
import pos.service.*;
import java.util.*;

public class TableManagementTUI implements UI {
    private TableService tableService;
    private Scanner scanner = new Scanner(System.in);

    public TableManagementTUI(TableService tableService) {
        this.tableService = tableService;
    }

    @Override 
    public void start() {
        while(true) {
            System.out.println("\n--- TABLE MANAGEMENT ---");
            System.out.println("1) View Tables");
            System.out.println("2) Assign Customer to Table");
            System.out.println("3) Clean Table");
            System.out.println("4) Add Table");
            System.out.println("5) Remove Table");
            System.out.println("0) Back");
            System.out.print("Choose: "); 
            String c = scanner.nextLine().trim();
            
            if (c.equals("1")) viewTables();
            else if (c.equals("2")) assignCustomerToTable();
            else if (c.equals("3")) cleanTable();
            else if (c.equals("4")) addTable();
            else if (c.equals("5")) removeTable();
            else if (c.equals("0")) return;
            else System.out.println("Invalid");
        }
    }

    private void viewTables() {
        System.out.println("\n--- TABLES ---");
        for (pos.model.Table t : tableService.all()) 
            System.out.println(t.toString());
    }

    private void assignCustomerToTable() {
        viewTables();
        System.out.print("Table id: "); 
        int id = Integer.parseInt(scanner.nextLine().trim());
        pos.model.Table t = tableService.findById(id);
        if (t==null) { System.out.println("Not found"); return; }
        if (!t.getStatus().equals("AVAILABLE")) { 
            System.out.println("Table not available"); 
            return; 
        }
        System.out.print("Number people: "); 
        int pc = Integer.parseInt(scanner.nextLine().trim());
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
        System.out.print("Table id to clean: "); 
        int id = Integer.parseInt(scanner.nextLine().trim());
        pos.model.Table t = tableService.findById(id);
        if (t==null) { System.out.println("Not found"); return; }
        if (!t.getStatus().equals("DIRTY")) { 
            System.out.println("Not dirty"); 
            return; 
        }
        t.setStatus("AVAILABLE"); 
        t.setCustomerCount(0); 
        tableService.saveAll(); 
        System.out.println("Cleaned.");
    }

    private void addTable() {
        System.out.print("Capacity: "); 
        int cap = Integer.parseInt(scanner.nextLine().trim());
        tableService.add(cap);
        System.out.println("Added table.");
    }

    private void removeTable() {
        viewTables();
        System.out.print("Enter table id to remove: "); 
        int id = Integer.parseInt(scanner.nextLine().trim());
        tableService.remove(id);
        System.out.println("Removed table if existed.");
    }
}