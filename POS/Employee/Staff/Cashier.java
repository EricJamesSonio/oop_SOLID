package POS.Employee.Staff;

import POS.Employee.Base.Employee;

public class Cashier extends Employee {

    public Cashier(String name, int id) {
        super(name, id);
    }

    @Override
    public String getDetails() {
        return String.format("Cashier Name : %s , Id : %d ", getName(), getId());
    }
}
