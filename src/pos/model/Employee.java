
package pos.model;

public class Employee {
    protected int id;
    protected String name;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId(){ return id; }
    public String getName(){ return name; }
    public String getRole(){ return "Employee"; }
    @Override
    public String toString(){ return id + " | " + name + " | " + getRole(); }
}
