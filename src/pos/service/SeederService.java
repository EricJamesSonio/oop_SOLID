
package pos.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Seeds minimal data into resources/data if files missing
 */
public class SeederService {
    private String base = "resources/data";
    public void seedIfNeeded(){
        File dir = new File(base);
        if (!dir.exists()) dir.mkdirs();
        try {
            seedFile("menu.txt", defaultMenu());
            seedFile("ingredientsStock.txt", defaultIngredients());
            seedFile("tables.txt", defaultTables());
            seedFile("employees.txt", defaultEmployees());
            seedFile("auth.txt", defaultAuth());
            seedFile("orders.txt", new ArrayList<String>());
            seedFile("sales.txt", new ArrayList<String>());
        } catch(Exception ex){ ex.printStackTrace(); }
    }
    private void seedFile(String name, List<String> lines) throws Exception {
        File f = new File(base + "/" + name);
        if (!f.exists()){
            FileUtil.writeAll(f.getPath(), lines);
        }
    }
    private List<String> defaultMenu(){
        List<String> l = new ArrayList<String>();
        // id|class|name|price|recipe (recipe: ingId:qty,...)
        l.add("1|FriedChickenLeg|Fried Chicken (Leg)|120.00|1:1"); // uses ingredient id 1
        l.add("2|FriedChickenBreast|Fried Chicken (Breast)|130.00|1:1"); 
        l.add("3|FriedChickenWhole|Fried Chicken (Whole)|420.00|1:4"); 
        l.add("4|Hotdog|Hotdog|45.00|2:1"); 
        l.add("5|Longganisa|Longganisa|60.00|3:1"); 
        l.add("6|Tocino|Tocino|55.00|4:1"); 
        l.add("7|Coke|Coke (330ml)|25.00|"); 
        l.add("8|Sprite|Sprite (330ml)|25.00|"); 
        l.add("9|Royal|Royal (330ml)|20.00|"); 
        return l;
    }
    private List<String> defaultIngredients(){
        List<String> l = new ArrayList<String>();
        // id|name|stock|unit|threshold
        l.add("1|Chicken (pieces)|50|pcs|10"); // used by maincourses
        l.add("2|Hotdog (pcs)|30|pcs|5"); 
        l.add("3|Longganisa (pcs)|30|pcs|5"); 
        l.add("4|Tocino (pcs)|30|pcs|5"); 
        // drinks as stock items (no recipe items)
        l.add("10|Coke (330ml)|40|bottle|10"); 
        l.add("11|Sprite (330ml)|35|bottle|10"); 
        l.add("12|Royal (330ml)|20|bottle|5"); 
        return l;
    }
    private List<String> defaultTables(){
        List<String> l = new ArrayList<String>();
        for (int i=1;i<=10;i++){
            l.add(i + "|4|AVAILABLE|0"); // default capacity 4 for simplicity
        }
        return l;
    }
    private List<String> defaultEmployees(){
        List<String> l = new ArrayList<String>();
        l.add("1|Administrator|Admin");
        l.add("2|Cashier|Cashier");
        return l;
    }
    private List<String> defaultAuth(){
        List<String> l = new ArrayList<String>();
        l.add("admin|admin123|1|Admin");
        l.add("cashier|cashier123|2|Cashier");
        return l;
    }
}
