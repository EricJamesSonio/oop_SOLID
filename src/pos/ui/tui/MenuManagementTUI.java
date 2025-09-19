package pos.ui.tui;

import pos.ui.UI;
import pos.model.Employee;
import pos.service.*;
import pos.model.MenuItem;
import pos.model.Recipe;
import pos.model.Ingredient;
import java.util.*;

public class MenuManagementTUI implements UI {
    private MenuService menu;
    private InventoryService inventory;
    private Scanner scanner = new Scanner(System.in);

    public MenuManagementTUI(MenuService menu, InventoryService inventory) {
        this.menu = menu;
        this.inventory = inventory;
    }

    @Override 
    public void start() {
        while(true) {
            System.out.println("\n--- MENU MANAGEMENT ---");
            System.out.println("1) View Menu");
            System.out.println("2) View Recipes");
            System.out.println("3) Add Menu Item");
            System.out.println("4) Remove Menu Item");
            System.out.println("5) Update Menu Item");
            System.out.println("0) Back");
            System.out.print("Choose: "); 
            String c = scanner.nextLine().trim();
            
            if (c.equals("1")) viewMenu();
            else if (c.equals("2")) viewRecipes();
            else if (c.equals("3")) addMenu();
            else if (c.equals("4")) removeMenu();
            else if (c.equals("5")) updateMenu();
            else if (c.equals("0")) return;
            else System.out.println("Invalid");
        }
    }

    private void viewMenu() {
        System.out.println("\n--- MENU ITEMS ---");
        // group by category
        Map<String, List<MenuItem>> groups = new LinkedHashMap<String, List<MenuItem>>();
        for (MenuItem m : menu.all()) {
            String cat = m.getCategory();
            if (!groups.containsKey(cat)) groups.put(cat, new ArrayList<MenuItem>());
            groups.get(cat).add(m);
        }
        for (Map.Entry<String,List<MenuItem>> e : groups.entrySet()) {
            System.out.println("\n--- " + e.getKey() + " ---");
            System.out.println("ID | Name | Price | Availability");
            for (MenuItem m : e.getValue()) {
                String avail = "AVAILABLE";
                boolean ok = true;
                if (m.getRecipe() == null || m.getRecipe().getItems().isEmpty()) {
                    pos.model.Ingredient ingFound = null;
                    for (pos.model.Ingredient i : inventory.all()) 
                        if (i.getName().toLowerCase().contains(m.getName().toLowerCase())) { 
                            ingFound = i; break; 
                        }
                    if (ingFound==null || ingFound.getStock() < 1) ok = false;
                } else {
                    for (Map.Entry<Integer,Integer> re : m.getRecipe().getItems().entrySet()) {
                        pos.model.Ingredient ing = inventory.findById(re.getKey());
                        if (ing==null || ing.getStock() < re.getValue()) { ok = false; break; }
                    }
                }
                if (!ok) avail = "NOT AVAILABLE";
                System.out.println(m.getId() + " | " + m.getName() + " | " + String.format("%.2f", m.getPrice()) + " | " + avail);
            }
        }
    }

    private void viewRecipes() {
        System.out.println("\n--- VIEW RECIPES ---");
        System.out.println("Select menu item to view ingredients:");
        for (MenuItem m : menu.all()) 
            System.out.println(m.getId() + " | " + m.getName() + " | " + m.getCategory());
        System.out.print("Menu id (0 to back): "); 
        String s = scanner.nextLine().trim();
        int id = Integer.parseInt(s);
        if (id==0) return;
        
        MenuItem m = menu.findById(id);
        if (m==null) { System.out.println("Not found"); return; }
        
        if (m.getRecipe()==null || m.getRecipe().getItems().isEmpty()) {
            pos.model.Ingredient drinkIng = null;
            for (Ingredient ing : inventory.all()) {
                if (ing.getName().toLowerCase().contains(m.getName().toLowerCase())) { 
                    drinkIng = ing; break; 
                }
            }
            if (drinkIng != null) 
                System.out.println(m.getName() + " -> " + drinkIng.getStock() + " " + drinkIng.getUnit());
            else 
                System.out.println("No recipe/ingredients for this item.");
            return;
        }
        
        System.out.println("Ingredients for: " + m.getName());
        for (Map.Entry<Integer,Integer> e : m.getRecipe().getItems().entrySet()) {
            pos.model.Ingredient ing = inventory.findById(e.getKey());
            if (ing != null) 
                System.out.println(" - " + ing.getName() + " -> " + e.getValue() + " " + ing.getUnit());
            else 
                System.out.println(" - Ingredient id="+e.getKey()+" missing in inventory");
        }
    }

    private void addMenu() {
        try {
            int id = menu.nextId();
            System.out.print("Name: "); String name = scanner.nextLine().trim();
            System.out.print("Price: "); double price = Double.parseDouble(scanner.nextLine().trim());
            System.out.println("Choose category: 1) Main Course 2) Side Dish 3) Drink");
            System.out.print("Choice: "); String ch = scanner.nextLine().trim();
            
            String cls = "";
            Recipe r = new Recipe();
            if (ch.equals("1")) cls = "MainCourse";
            else if (ch.equals("2")) cls = "SideDish";
            else if (ch.equals("3")) cls = "Drink";
            
            boolean isDrink = cls.equals("Drink");
            if (!isDrink) {
                System.out.println("Select ingredient ids and qty (format id:qty,id:qty). Available ingredients:");
                for (Ingredient ing : inventory.all()) System.out.println(ing.toString());
                System.out.print("Recipe: "); String rec = scanner.nextLine().trim();
                r = Recipe.parse(rec);
            }
            
            MenuItem mi = null;
            if (cls.equals("MainCourse")) mi = new pos.model.MainCourse(id,name,price,r);
            else if (cls.equals("SideDish")) mi = new pos.model.SideDish(id,name,price,r);
            else if (cls.equals("Drink")) mi = new pos.model.Drink(id,name,price);
            
            menu.add(mi, cls);
            System.out.println("Added.");
        } catch(Exception ex) { 
            System.out.println("Failed to add: " + ex.getMessage()); 
        }
    }

    private void removeMenu() {
        viewMenu();
        System.out.print("Enter menu id to remove: "); 
        int id = Integer.parseInt(scanner.nextLine().trim());
        menu.remove(id);
        System.out.println("Removed if existed.");
    }

    private void updateMenu() {
        viewMenu();
        System.out.print("Enter menu id to update: "); 
        int id = Integer.parseInt(scanner.nextLine().trim());
        MenuItem m = menu.findById(id);
        if (m==null) { System.out.println("Not found"); return; }
        
        System.out.print("New name (enter to keep): "); String name = scanner.nextLine().trim();
        System.out.print("New price (enter to keep): "); String p = scanner.nextLine().trim();
        
        if (name.length()>0) m.setName(name);
        if (p.length()>0) m.setPrice(Double.parseDouble(p));
        
        menu.update(m, m.getClass().getSimpleName());
        System.out.println("Updated.");
    }
}