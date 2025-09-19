
package pos.service;

import pos.model.Table;
import java.util.*;

public class TableService {
    private String path = "resources/data/tables.txt";
    private List<Table> tables = new ArrayList<Table>();

    public TableService(){ load(); }

    public void load(){
        tables.clear();
        List<String> lines = FileUtil.readAllLines(path);
        for (String l : lines){
            // id|capacity|status|customerCount
            String[] p = l.split("\\|", -1);
            try {
                int id = Integer.parseInt(p[0]);
                int cap = Integer.parseInt(p[1]);
                String status = p[2];
                int cc = Integer.parseInt(p[3]);
                tables.add(new Table(id,cap,status,cc));
            } catch(Exception ex) {}
        }
    }
    public List<Table> all(){ return tables; }
    public Table findById(int id){
        for (Table t : tables) if (t.getId()==id) return t;
        return null;
    }
    public void saveAll(){
        List<String> lines = new ArrayList<String>();
        for (Table t : tables){
            lines.add(t.getId()+"|"+t.getCapacity()+"|"+t.getStatus()+"|"+t.getCustomerCount());
        }
        FileUtil.writeAll(path, lines);
    }
    public void add(int capacity){
        int max = 0;
        for (Table t : tables) if (t.getId()>max) max = t.getId();
        int id = max+1;
        tables.add(new Table(id, capacity, "AVAILABLE", 0));
        saveAll();
    }
    public void remove(int id){
        List<Table> out = new ArrayList<Table>();
        for (Table t: tables) if (t.getId()!=id) out.add(t);
        tables = out;
        saveAll();
    }
}
