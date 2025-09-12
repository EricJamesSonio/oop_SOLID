package POS.Orders.Management.Loader;

import POS.Orders.Base.CustomerType;
import POS.Orders.Models.Order;
import POS.Orders.Models.OrderItem;
import POS.Orders.Management.OrderRepository;
import POS.Orders.Services.DiscountCalculator;
import POS.Orders.Services.DiscountFactory;
import POS.Menu.Base.MenuItem;
import POS.Menu.Services.MenuService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class OrderJsonLoader {
    private final OrderRepository orderRepository = new OrderRepository();
    private final MenuService menuService; 

    public OrderJsonLoader(String jsonFilePath, MenuService menuService) {
        this.menuService = menuService;
        loadFromJson(jsonFilePath);
    }

    private void loadFromJson(String path) {
        try {
            File file = new File(path);
            System.out.println("DEBUG: orders file path: " + file.getAbsolutePath() +
                    " exists=" + file.exists() + " size=" + file.length());

            if (!file.exists()) {
                System.out.println("⚠️ Orders file not found: " + file.getAbsolutePath());
                return;
            }

            // Read entire file
            String content = java.nio.file.Files.readString(file.toPath());
            if (content == null) content = "";
            String trimmed = content.trim();

            if (trimmed.isEmpty()) {
                System.out.println("⚠️ Orders file is empty: " + file.getAbsolutePath());
                return;
            }

            // compact (no whitespace) for top-level checks
            String compact = trimmed.replaceAll("\\s+", "");

            // consider {} or [] as empty JSON content (do not fail)
            if (compact.equals("[]") || compact.equals("{}")) {
                System.out.println("⚠️ Orders file contains empty JSON ([], {}) — no orders to load.");
                return;
            }

            // If the file contains an object with "orders":[ ... ], extract it.
            if (compact.startsWith("{") && compact.contains("\"orders\":[")) {
                String ordersArray = extractArray(trimmed, "orders");
                if (ordersArray == null || ordersArray.isBlank()) {
                    System.out.println("⚠️ 'orders' property present but empty.");
                    return;
                }
                // make a top-level array string
                content = "[" + ordersArray + "]";
            } else if (compact.startsWith("{") && !compact.startsWith("[")) {
                // single order object -> wrap it into an array
                content = "[" + trimmed + "]";
            }

            // now ensure we have the inner array body (strip outer [ ])
            if (content.startsWith("[")) content = content.substring(1);
            if (content.endsWith("]")) content = content.substring(0, content.length() - 1);

            if (content.isBlank()) {
                System.out.println("⚠️ No valid orders found after processing JSON.");
                return;
            }

            String[] ordersData = content.split("\\},\\s*\\{");
            for (String rawOrder : ordersData) {
                if (!rawOrder.startsWith("{")) rawOrder = "{" + rawOrder;
                if (!rawOrder.endsWith("}")) rawOrder = rawOrder + "}";
                Order order = parseOrder(rawOrder);
                if (order != null) {
                    orderRepository.addOrder(order); // add even if it ends up having zero items — safer than crashing
                }
            }

        } catch (java.io.IOException e) {
            System.err.println("⚠️ Error reading orders file: " + e.getMessage());
            e.printStackTrace();
        }
    }



    private Order parseOrder(String data) {
        try {
            int id = safeParseInt(extractValue(data, "id"), 0);
            String typeStr = extractValue(data, "customerType");

            // ✅ Map string to CustomerType object
            CustomerType customerType = switch (typeStr.toUpperCase()) {
                case "PWD" -> new CustomerType(true, false);
                case "SENIOR" -> new CustomerType(false, true);
                case "PWD_SENIOR" -> new CustomerType(true, true);
                default -> new CustomerType(false, false); // REGULAR
            };

            // ✅ Use DiscountFactory
            DiscountCalculator discountCalculator =
                    DiscountFactory.createDiscountCalculator(customerType);

            Order order = new Order(customerType, id, discountCalculator);

            // ✅ items section may be missing or empty
            String itemsSection = extractArray(data, "items");
            if (itemsSection != null && !itemsSection.isBlank()) {
                String[] itemEntries = itemsSection.split("\\},\\s*\\{");
                for (String rawItem : itemEntries) {
                    if (!rawItem.startsWith("{")) rawItem = "{" + rawItem;
                    if (!rawItem.endsWith("}")) rawItem = rawItem + "}";

                    int code = safeParseInt(extractValue(rawItem, "code"), -1);
                    int qty = safeParseInt(extractValue(rawItem, "qty"), 0);

                    if (code < 0 || qty <= 0) continue;

                    // ✅ Resolve real MenuItem from MenuService
                    MenuItem menuItem = menuService.getMenuItemByCode(code);

                    if (menuItem != null) {
                        order.addItem(menuItem, qty);
                    } else {
                        System.err.println("⚠️ No menu item found for code: " + code);
                    }
                }
            }

            return order;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // --- tiny string extractors ---
    private String extractValue(String data, String key) {
        try {
            String search = "\"" + key + "\":";
            int start = data.indexOf(search);
            if (start < 0) return "";
            start += search.length();
            if (start >= data.length()) return "";

            char ch = data.charAt(start);
            if (ch == '"') {
                int end = data.indexOf('"', start + 1);
                if (end < 0) return "";
                return data.substring(start + 1, end);
            } else {
                int end = start;
                while (end < data.length() && ",}]".indexOf(data.charAt(end)) == -1) end++;
                return data.substring(start, end).trim();
            }
        } catch (Exception e) {
            return "";
        }
    }

    private String extractArray(String data, String key) {
        try {
            String search = "\"" + key + "\":[";
            int start = data.indexOf(search);
            if (start < 0) return null;
            start += search.length();
            int end = data.indexOf("]", start);
            if (end < 0) return null;
            return data.substring(start, end).trim();
        } catch (Exception e) {
            return null;
        }
    }

    private int safeParseInt(String value, int defaultVal) {
        try {
            return Integer.parseInt(value.trim());
        } catch (Exception e) {
            return defaultVal;
        }
    }

    public OrderRepository toRepository() {
        return orderRepository;
    }
}
