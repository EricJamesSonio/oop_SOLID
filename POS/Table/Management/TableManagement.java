package POS.Table.Management;
import POS.Table.Models.Table;
import POS.Reporting.Printer.Printer;
import java.util.*;

public class TableManagement {
    private List<Table> tables;
    private static int nextId = 1;
    
    public TableManagement() {
        this.tables = new ArrayList<>();
    }

    public void addTable(Table newtable) {
        Table existing = findTable(newtable.getId());

        if (existing != null) {
            Printer.printMessage("Table Already Exist! ");
            return;
        } 

        tables.add(newtable);
    }

    public boolean removeTable(int id) {
        Table existing = findTable(id);
        if (existing != null) {
            tables.remove(existing);
            return true;
        } else {
            Printer.printMessage("Table Doesn't Exist! ");
            return false;
        }
    }

    public void createTable(int capacity) {
        int id = nextId++;
        Table newtable = new Table(id, capacity);
        tables.add(newtable);
    }
 
    public Table findTable(int id) {
        for (Table t : tables) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    } 

    public List<Table> getTables() {
        return tables;
    }

    public Table getTable(int id) {
        Table existing = findTable(id);
        
        if (existing != null) {
            return existing;
        } else {
            return null;
        }
        
    }
}
