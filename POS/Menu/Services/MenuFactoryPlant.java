package POS.Menu.Services;

import POS.Inventory.Ingredients.Recipe;
import POS.Menu.Base.MenuItem;
import POS.Menu.Items.Drink;
import POS.Menu.Items.Dessert;
import POS.Menu.Items.MainDish;
import POS.Menu.Items.SideDish;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuFactoryPlant {
    private final List<MenuItem> menuItems = new ArrayList<>();

    public MenuFactoryPlant(String jsonFilePath) {
        loadFromJson(jsonFilePath);
    }

    private void loadFromJson(String path) {
        try {
            File file = new File(path);

            if (!file.exists() || file.length() == 0) {
                System.out.println("⚠️ Menu file not found or empty: " + path);
                return;
            }

            Scanner scanner = new Scanner(file);
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine());
            }
            scanner.close();
            String content = sb.toString().trim();

            // ✅ Exit if file only has [] or empty
            if (content.isEmpty() || content.equals("[]")) {
                System.out.println("⚠️ No menu items available in " + path);
                return;
            }

            // Strip outer [ ]
            if (content.startsWith("[")) content = content.substring(1);
            if (content.endsWith("]")) content = content.substring(0, content.length() - 1);

            if (content.isBlank()) {
                System.out.println("⚠️ No valid menu entries found.");
                return;
            }

            String[] itemsData = content.split("\\},\\s*\\{");

            for (String rawItem : itemsData) {
                if (!rawItem.startsWith("{")) rawItem = "{" + rawItem;
                if (!rawItem.endsWith("}")) rawItem = rawItem + "}";
                MenuItem item = parseMenuItem(rawItem);
                if (item != null) menuItems.add(item);
            }

        } catch (FileNotFoundException e) {
            System.err.println("⚠️ Menu file not found: " + path);
        }
    }


    private MenuItem parseMenuItem(String data) {
        String type = extractValue(data, "type");
        String name = extractValue(data, "name");
        int code = Integer.parseInt(extractValue(data, "code"));
        double price = Double.parseDouble(extractValue(data, "price"));
        String desc = extractValue(data, "description");
        LocalDate exp = LocalDate.parse(extractValue(data, "expDate"));
        Recipe recipe = new Recipe(); // placeholder

        return switch (type.toLowerCase()) {
            case "drink" -> new Drink(name, code, price, desc, exp, recipe);
            case "dessert" -> new Dessert(name, code, price, desc, exp, recipe);
            case "maindish" -> new MainDish(name, code, price, desc, exp, recipe);
            case "sidedish" -> new SideDish(name, code, price, desc, exp, recipe);
            default -> null;
        };
    }

    private String extractValue(String data, String key) {
        String search = "\"" + key + "\":";
        int start = data.indexOf(search) + search.length();
        if (start < search.length()) return "";
        char ch = data.charAt(start);
        if (ch == '"') { // string
            int end = data.indexOf('"', start + 1);
            return data.substring(start + 1, end);
        } else { // number
            int end = start;
            while (end < data.length() && ",}".indexOf(data.charAt(end)) == -1) end++;
            return data.substring(start, end);
        }
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItems;
    }

    public MenuItem getByCode(int code) {
        for (MenuItem item : menuItems) {
            if (item.getCode() == code) return item;
        }
        return null;
    }
}
