package pos.service;

import pos.model.Employee;
import pos.model.Admin;
import pos.model.Cashier;
import java.util.List;
import java.util.ArrayList;


public class EmployeeService {
    private String path = "resources/data/employees.txt";
    private List<Employee> employees = new ArrayList<Employee>();

    public EmployeeService(){ load(); }

    public void load(){
        employees.clear();
        List<String> lines = FileUtil.readAllLines(path);
        for (String l : lines){
            String[] p = l.split("\\|", -1);
            try {
                int id = Integer.parseInt(p[0]);
                String name = p[1];
                String role = p[2];
                if ("Admin".equals(role)) employees.add(new Admin(id,name));
                else if ("Cashier".equals(role)) employees.add(new Cashier(id,name));
                else employees.add(new Employee(id,name));
            } catch(Exception ex){}
        }
    }
    public List<Employee> all(){ return employees; }
    public Employee findById(int id){
        for (Employee e: employees) if (e.getId()==id) return e;
        return null;
    }
    public int nextId(){
        int max = 0;
        for (Employee e: employees) if (e.getId()>max) max = e.getId();
        return max+1;
    }
    public void add(Employee e, String role){
        String line = e.getId()+"|"+e.getName()+"|"+role;
        FileUtil.appendLine(path, line);
        load();
    }
    public void remove(int id){
        List<String> lines = FileUtil.readAllLines(path);
        List<String> out = new ArrayList<String>();
        for (String l : lines){
            String[] p = l.split("\\|", -1);
            try { int lid = Integer.parseInt(p[0]); if (lid!=id) out.add(l); } catch(Exception ex){ out.add(l); }
        }
        FileUtil.writeAll(path, out);
        load();
    }
}
