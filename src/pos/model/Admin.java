
package pos.model;

public class Admin extends Employee {
    public Admin(int id, String name) {
        super(id, name);
    }
    @Override
    public String getRole(){ return "Admin"; }
}
