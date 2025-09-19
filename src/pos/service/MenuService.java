
package pos.service;

import pos.model.*;
import java.util.*;
import java.io.*;

public class MenuService {
    private String path = "resources/data/menu.txt";
    private List<MenuItem> items = new ArrayList<MenuItem>();
    private InventoryService inventory;

    public MenuService(InventoryService inventory){
        this.inventory = inventory;
        load();
    }
    public void load(){
        items.clear();
        List<String> lines = FileUtil.readAllLines(path);
        for (String l : lines){
            // id|class|name|price|recipe
            String[] p = l.split("\\|", -1);
            try {
                int id = Integer.parseInt(p[0]);
                String cls = p[1];
                String name = p[2];
                double price = Double.parseDouble(p[3]);
                String recipe = p.length>4? p[4] : "";
                Recipe r = Recipe.parse(recipe);
                MenuItem mi = null;
                if ("FriedChickenLeg".equals(cls)) mi = new FriedChickenLeg(id,name,price,r);
                else if ("FriedChickenBreast".equals(cls)) mi = new FriedChickenBreast(id,name,price,r);
                else if ("FriedChickenWhole".equals(cls)) mi = new FriedChickenWhole(id,name,price,r);
                else if ("Hotdog".equals(cls)) mi = new Hotdog(id,name,price,r);
                else if ("Longganisa".equals(cls)) mi = new Longganisa(id,name,price,r);
                else if ("Tocino".equals(cls)) mi = new Tocino(id,name,price,r);
                else if ("Coke".equals(cls)) mi = new Coke(id,name,price);
                else if ("Sprite".equals(cls)) mi = new Sprite(id,name,price);
                else if ("Royal".equals(cls)) mi = new Royal(id,name,price);
                else {
                    // fallback based on category
                    if (r==null || r.getItems().isEmpty()) mi = new Drink(id,name,price);
                    else mi = new MenuItem(id,name,price,r);
                }
                items.add(mi);
            } catch(Exception ex){ ex.printStackTrace(); }
        }
    }
    public List<MenuItem> all(){ return items; }
    public MenuItem findById(int id){
        for (MenuItem m: items) if (m.getId()==id) return m;
        return null;
    }
    public void add(MenuItem m, String className){
        // append to file. we store class simpleName to reconstruct class next load
        String recipe = (m.getRecipe()==null?"":m.getRecipe().serialize());
        String line = m.getId()+"|"+className+"|"+m.getName()+"|"+String.format("%.2f", m.getPrice())+"|"+recipe;
        FileUtil.appendLine(path, line);
        load();
    }
    public void remove(int id){
        List<String> lines = FileUtil.readAllLines(path);
        List<String> out = new ArrayList<String>();
        for (String l : lines){
            String[] p = l.split("\\|", -1);
            try {
                int lid = Integer.parseInt(p[0]);
                if (lid != id) out.add(l);
            } catch(Exception ex){ out.add(l); }
        }
        FileUtil.writeAll(path, out);
        load();
    }
    public void update(MenuItem m, String className){
        List<String> lines = FileUtil.readAllLines(path);
        List<String> out = new ArrayList<String>();
        for (String l : lines){
            String[] p = l.split("\\|", -1);
            try {
                int lid = Integer.parseInt(p[0]);
                if (lid == m.getId()){
                    String recipe = (m.getRecipe()==null?"":m.getRecipe().serialize());
                    out.add(m.getId()+"|"+className+"|"+m.getName()+"|"+String.format("%.2f", m.getPrice())+"|"+recipe);
                } else out.add(l);
            } catch(Exception ex){ out.add(l); }
        }
        FileUtil.writeAll(path, out);
        load();
    }
    public int nextId(){
        int max = 0;
        for (MenuItem m: items) if (m.getId()>max) max = m.getId();
        return max+1;
    }
}
