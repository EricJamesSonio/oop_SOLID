
package pos.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Order {
    private int id;
    private int createdByEmployeeId;
    private int tableId; // 0 for walk-in
    private List<OrderItem> items = new ArrayList<OrderItem>();
    private String status; // PENDING, COMPLETED, REFUNDED, DELETED
    private double discountAmount; // absolute value
    private boolean seniorOrPwd;
    private String discountType; // NONE, PWD, SENIOR
    private boolean paid;
    private Date createdAt = new Date();

    public Order(int id, int createdByEmployeeId, int tableId){
        this.id = id; this.createdByEmployeeId = createdByEmployeeId; this.tableId = tableId; this.status = "PENDING";
    }
    public void addItem(OrderItem it){ items.add(it); }
    public List<OrderItem> getItems(){ return items; }
    public int getId(){ return id; }
    public int getCreatedBy(){ return createdByEmployeeId; }
    public int getTableId(){ return tableId; }
    public String getStatus(){ return status; }
    public void setStatus(String s){ this.status = s; }
    public void setDiscountAmount(double d){ this.discountAmount = d; }
    public double getDiscountAmount(){ return discountAmount; }
    public void setSeniorOrPwd(boolean v){ this.seniorOrPwd = v; }
    public boolean isSeniorOrPwd(){ return seniorOrPwd; }
    public double getSubtotal(){
        double s = 0.0;
        for (OrderItem it : items) s += it.getSubtotal();
        return s;
    }
    public double getTotal(){
        return getSubtotal() - discountAmount;
    }

    public void setDiscountType(String dt){ this.discountType = dt; }
    public String getDiscountType(){ return discountType == null ? "NONE" : discountType; }
    public void setPaid(boolean v){ this.paid = v; }
    public boolean isPaid(){ return paid; }
    public String getCreatedAtString(){
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(createdAt);
    }
    public String serializeItems(){
        // item1;item2 where item is id|name|qty|price
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (OrderItem it : items){
            if (!first) sb.append(";"); else first=false;
            sb.append(it.toString());
        }
        return sb.toString();
    }
    public static Order parse(String line){
        try{
            // id|createdBy|tableId|status|subtotal|discountAmount|total|isPaid|createdAt|discountType|item1;item2
            String[] p = line.split("\\|", 11);
            int id = Integer.parseInt(p[0]);
            int createdBy = Integer.parseInt(p[1]);
            int tableId = Integer.parseInt(p[2]);
            Order o = new Order(id, createdBy, tableId);
            if (p.length>3) o.setStatus(p[3]);
            try{ if (p.length>4 && p[4].length()>0) {/*subtotal*/} } catch(Exception ex){}
            try{ if (p.length>5 && p[5].length()>0) o.setDiscountAmount(Double.parseDouble(p[5])); } catch(Exception ex){}
            try{ if (p.length>6 && p[6].length()>0) {/*total*/} } catch(Exception ex){}
            try{ if (p.length>7 && p[7].length()>0) o.setPaid(Boolean.parseBoolean(p[7])); } catch(Exception ex){}
            if (p.length>8 && p[8] != null && p[8].length()>0){
                try {
                    java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    o.createdAt = fmt.parse(p[8]);
                } catch(Exception ex){}
            }
            if (p.length>9) o.setDiscountType(p[9]);
            String itemsStr = p.length>10? p[10] : "";
            if (itemsStr != null && itemsStr.length()>0){
                String[] its = itemsStr.split(";");
                for (int i=0;i<its.length;i++){
                    OrderItem oi = OrderItem.parse(its[i]);
                    if (oi!=null) o.addItem(oi);
                }
            }
            return o;
        }catch(Exception ex){
            return null;
        }
    }
}
