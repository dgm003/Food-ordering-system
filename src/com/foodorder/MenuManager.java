package com.foodorder;

import java.sql.*;
import java.util.Scanner;

public class MenuManager {
    private Scanner scanner;

    public MenuManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public void addMenuItem() {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();

        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); 

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO menu_items (name, price) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setDouble(2, price);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println(" Menu item added successfully!");
            }
        } catch (SQLException e) {
            System.out.println(" Error while adding item");
            e.printStackTrace();
        }
    }

    public void viewMenu() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT item_id, name, price FROM menu_items";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n🍽️ Menu:");
            while (rs.next()) {
                System.out.printf("ID: %d | Name: %s | Price: ₹%.2f\n",
                        rs.getInt("item_id"), rs.getString("name"), rs.getDouble("price"));
            }
        } catch (SQLException e) {
            System.out.println(" Error while retrieving menu");
            e.printStackTrace();
        }
    }

    public void placeOrder() {
    try (Connection conn = DBConnection.getConnection()) {

        System.out.print("Enter your name: ");
        String customerName = scanner.nextLine();

        double totalAmount = 0.0;

        // Step 1: Insert order with initial total_amount = 0
        String orderSql = "INSERT INTO orders (customer_name, total_amount) VALUES (?, ?)";
        PreparedStatement orderStmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
        orderStmt.setString(1, customerName);
        orderStmt.setDouble(2, totalAmount); 
        orderStmt.executeUpdate();

        ResultSet generatedKeys = orderStmt.getGeneratedKeys();
        if (!generatedKeys.next()) {
            System.out.println(" Failed to create order.");
            return;
        }

        int orderId = generatedKeys.getInt(1);

        // Step 2: Looping for multiple items
        boolean addMore = true;
        while (addMore) {
            System.out.print("Enter item ID to order: ");
            int itemId = scanner.nextInt();
            scanner.nextLine(); 

            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); 

            // Fetch price of item
            String priceQuery = "SELECT price FROM menu_items WHERE item_id = ?";
            PreparedStatement priceStmt = conn.prepareStatement(priceQuery);
            priceStmt.setInt(1, itemId);
            ResultSet priceRs = priceStmt.executeQuery();

            if (priceRs.next()) {
                double price = priceRs.getDouble("price");
                totalAmount += price * quantity;

                // Inserting contents into order_items
                String itemSql = "INSERT INTO order_items (order_id, item_id, quantity) VALUES (?, ?, ?)";
                PreparedStatement itemStmt = conn.prepareStatement(itemSql);
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, itemId);
                itemStmt.setInt(3, quantity);
                itemStmt.executeUpdate();
            } else {
                System.out.println(" Invalid item ID: " + itemId);
            }

            // Asking the user if they want to add more
            System.out.print("Add more items? (yes/no): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            addMore = choice.equals("yes");
        }

        // Step 3: Updating final total in the order
        String updateTotalSql = "UPDATE orders SET total_amount = ? WHERE order_id = ?";
        PreparedStatement updateStmt = conn.prepareStatement(updateTotalSql);
        updateStmt.setDouble(1, totalAmount);
        updateStmt.setInt(2, orderId);
        updateStmt.executeUpdate();

        System.out.println(" Order placed successfully!");
        System.out.printf("🧾 Total Amount: ₹%.2f\n", totalAmount);

    } catch (SQLException e) {
        System.out.println(" Error while placing order");
        e.printStackTrace();
        }
    }

    public void viewOrders() {
    try (Connection conn = DBConnection.getConnection()) {
        String orderSql = "SELECT * FROM orders ORDER BY order_time DESC";
        PreparedStatement orderStmt = conn.prepareStatement(orderSql);
        ResultSet ordersRs = orderStmt.executeQuery();

        System.out.println("\n📦 Orders:");

        while (ordersRs.next()) {
            int orderId = ordersRs.getInt("order_id");
            String customerName = ordersRs.getString("customer_name");
            String orderTime = ordersRs.getString("order_time");
            double totalAmount = ordersRs.getDouble("total_amount");

            System.out.printf("\n🆔 Order ID: %d\n👤 Customer: %s\n🕒 Time: %s\n💰 Total: ₹%.2f\n", 
                orderId, customerName, orderTime, totalAmount);
            System.out.println("📝 Items:");

            String itemsSql = "SELECT m.name, oi.quantity, m.price FROM order_items oi JOIN menu_items m ON oi.item_id = m.item_id WHERE oi.order_id = ?";
            PreparedStatement itemsStmt = conn.prepareStatement(itemsSql);
            itemsStmt.setInt(1, orderId);
            ResultSet itemsRs = itemsStmt.executeQuery();

            while (itemsRs.next()) {
                String itemName = itemsRs.getString("name");
                int quantity = itemsRs.getInt("quantity");
                double price = itemsRs.getDouble("price");

                System.out.printf("   - %s × %d (₹%.2f each)\n", itemName, quantity, price);
            }
        }
    } catch (SQLException e) {
        System.out.println(" Error while retrieving orders");
        e.printStackTrace();
    }
}


}
