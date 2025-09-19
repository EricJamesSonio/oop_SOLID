
package pos.model;

public class Table {
    private int id;
    private int capacity;
    private String status; // AVAILABLE, OCCUPIED, DIRTY
    private int customerCount;

    public Table(int id, int capacity, String status, int customerCount){
        this.id = id; this.capacity = capacity; this.status = status; this.customerCount = customerCount;
    }
    public int getId(){ return id; }
    public int getCapacity(){ return capacity; }
    public String getStatus(){ return status; }
    public void setStatus(String s){ this.status = s; }
    public int getCustomerCount(){ return customerCount; }
    public void setCustomerCount(int c){ this.customerCount = c; }
    @Override public String toString(){ return id + " | cap:" + capacity + " | " + status + (status.equals("OCCUPIED")?" | customers:"+customerCount:"" ); }
}
