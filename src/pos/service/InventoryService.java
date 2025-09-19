package pos.service;

import pos.model.Ingredient;
import java.util.ArrayList;
import java.util.List;

public class InventoryService {
    private String path = "resources/data/ingredientsStock.txt";
    private List<Ingredient> items = new ArrayList<>();

    public InventoryService() {
        load();
    }

    public void load() {
        items.clear();
        List<String> lines = FileUtil.readAllLines(path);
        for (String l : lines) {
            // id|name|stock|unit|threshold
            String[] p = l.split("\\|", -1);
            try {
                int id = Integer.parseInt(p[0]);
                String name = p[1];
                int stock = Integer.parseInt(p[2]);
                String unit = p[3];
                int threshold = Integer.parseInt(p[4]);
                items.add(new Ingredient(id, name, stock, unit, threshold));
            } catch (Exception ex) {
                // ignore bad lines
            }
        }
    }

    public List<Ingredient> all() {
        return items;
    }

    public Ingredient findById(int id) {
        for (Ingredient ing : items) {
            if (ing.getId() == id) return ing;
        }
        return null;
    }

    public void saveAll() {
        List<String> lines = new ArrayList<>();
        for (Ingredient ing : items) {
            lines.add(ing.getId() + "|" + ing.getName() + "|" + ing.getStock() + "|" 
                    + ing.getUnit() + "|" + ing.getThreshold());
        }
        FileUtil.writeAll(path, lines);
    }

    public boolean deduct(int ingredientId, int qty) {
        Ingredient ing = findById(ingredientId);
        if (ing == null) return false;
        if (ing.getStock() < qty) return false;

        ing.setStock(ing.getStock() - qty);

        if (ing.getStock() == 0) {
            // stock depleted — kept for reference but not removed
        }

        saveAll();
        return true;
    }

    public void increase(int ingredientId, int qty) {
        Ingredient ing = findById(ingredientId);
        if (ing == null) return;
        ing.setStock(ing.getStock() + qty);
        saveAll();
    }
}
