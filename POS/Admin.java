package POS;

public class Admin extends Employee{
    public Admin (String name, int id) {
        super(name, id);
    }

    @Override
    public String getDetails() {
        return String.format("Admin Name : %s , Id : %d" , getName() ,getId());
    }
}
