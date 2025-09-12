package POS.Inventory.Loader;

import POS.Inventory.Ingredients.Ingredient;
import POS.Inventory.Management.InventoryManagement;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;

public class IngredientJsonLoader {
    private final InventoryManagement inventory;

    public IngredientJsonLoader(InventoryManagement inventory, String jsonFilePath) {
        this.inventory = inventory;
        loadFromJson(jsonFilePath);
    }

    private void loadFromJson(String path) {
        try {
            File file = new File(path);
            if (!file.exists() || file.length() == 0) {
                System.out.println("⚠️ Ingredient JSON not found or empty: " + path);
                return;
            }

            String content = Files.readString(file.toPath()).trim();
            if (content.isEmpty()) return;

            // Remove starting/ending brackets and split into individual JSON objects
            content = content.substring(1, content.length() - 1).trim(); // remove [ and ]
            String[] rawIngredients = content.split("\\},\\s*\\{");

            for (String raw : rawIngredients) {
                // Ensure each raw ingredient string is a valid JSON object
                if (!raw.startsWith("{")) raw = "{" + raw;
                if (!raw.endsWith("}")) raw = raw + "}";

                Ingredient ingredient = parseIngredient(raw);
                if (ingredient != null) {
                    inventory.addIngredientAmount(ingredient, 10); // initial stock 0
                    System.out.println("Loaded ingredient: " + ingredient.getName());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Ingredient parseIngredient(String data) {
        try {
            String name = extractString(data, "name");
            int code = extractInt(data, "code", -1);
            double price = extractDouble(data, "price", 0.0);
            String unit = extractString(data, "unitStock");
            String expStr = extractString(data, "expDate");
            LocalDate exp = expStr.isEmpty() ? LocalDate.now().plusYears(1) : LocalDate.parse(expStr);

            if (code < 0 || name.isEmpty()) return null;
            return new Ingredient(name, code, price, unit, exp);

        } catch (Exception e) {
            System.out.println("Failed to parse ingredient: " + data);
            return null;
        }
    }

    // --- tiny extractors ---
    private String extractString(String data, String key) {
        String search = "\"" + key + "\":";
        int start = data.indexOf(search);
        if (start < 0) return "";
        start += search.length();

        // Remove any leading whitespace
        while (start < data.length() && Character.isWhitespace(data.charAt(start))) start++;

        // Handle quoted string
        if (data.charAt(start) == '"') {
            int end = data.indexOf('"', start + 1);
            return end > start ? data.substring(start + 1, end).trim() : "";
        } else {
            // Unquoted value (numbers)
            int end = data.indexOf(',', start);
            if (end == -1) end = data.indexOf('}', start);
            return end > start ? data.substring(start, end).trim() : "";
        }
    }

    private int extractInt(String data, String key, int defaultVal) {
        try { return Integer.parseInt(extractString(data, key).trim()); } catch (Exception e) { return defaultVal; }
    }

    private double extractDouble(String data, String key, double defaultVal) {
        try { return Double.parseDouble(extractString(data, key).trim()); } catch (Exception e) { return defaultVal; }
    }
}
