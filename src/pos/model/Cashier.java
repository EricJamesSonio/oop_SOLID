
package pos.model;

public class Cashier extends Employee {
    public Cashier(int id, String name) {
        super(id, name);
    }
    @Override
    public String getRole(){ return "Cashier"; }
}
