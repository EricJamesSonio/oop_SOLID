
package pos.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Simple recipe: map ingredientId -> requiredQuantity (int)
 */
public class Recipe {
    private Map<Integer, Integer> items = new LinkedHashMap<Integer,Integer>();

    public Recipe(){}

    public void addIngredient(int ingredientId, int qty){
        items.put(ingredientId, qty);
    }
    public Map<Integer,Integer> getItems(){ return items; }

    public String serialize(){
        // format: ingId:qty,ingId:qty
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<Integer,Integer> e : items.entrySet()){
            if (!first) sb.append(",");
            sb.append(e.getKey()).append(":").append(e.getValue());
            first = false;
        }
        return sb.toString();
    }

    public static Recipe parse(String s){
        Recipe r = new Recipe();
        if (s == null || s.trim().length()==0) return r;
        String[] parts = s.split(",");
        for (int i=0;i<parts.length;i++){
            String p = parts[i].trim();
            if (p.length()==0) continue;
            String[] kv = p.split(":");
            try {
                int id = Integer.parseInt(kv[0]);
                int q = Integer.parseInt(kv[1]);
                r.addIngredient(id, q);
            } catch(Exception ex) {
                // ignore malformed
            }
        }
        return r;
    }
}
