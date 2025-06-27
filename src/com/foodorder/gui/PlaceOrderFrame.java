package com.foodorder.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.List;

import com.foodorder.DBConnection;
import com.foodorder.gui.PlaceOrderFrame.OrderItem;

public class PlaceOrderFrame extends JFrame {
    
    private JTextField txtCustomerName, txtItemId, txtQuantity;
    private JTextArea orderSummary;
    private JLabel lblStatus;

    private List<OrderItem> orderItems = new ArrayList<>();
    private double totalAmount = 0.0;
    private int orderId = -1;

    public PlaceOrderFrame() {
        
        
        setTitle("Place Order - Food Ordering System");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));

        txtCustomerName = new JTextField();
        txtItemId = new JTextField();
        txtQuantity = new JTextField();

        JButton btnAddItem = new JButton("Add Item");
        JButton btnSubmitOrder = new JButton("Submit Order");
        JButton btnClear = new JButton("Clear");

        orderSummary = new JTextArea(10, 30);
        orderSummary.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderSummary);

        lblStatus = new JLabel("Status: Ready");

        panel.add(new JLabel("Customer Name:"));
        panel.add(txtCustomerName);
        panel.add(new JLabel("Item ID:"));
        panel.add(txtItemId);
        panel.add(new JLabel("Quantity:"));
        panel.add(txtQuantity);
        panel.add(btnAddItem);
        panel.add(btnSubmitOrder);
        panel.add(btnClear);
        panel.add(scrollPane);
        panel.add(lblStatus);

        add(panel);

        // Add Item
        btnAddItem.addActionListener(e -> {
            try {
                int itemId = Integer.parseInt(txtItemId.getText());
                int quantity = Integer.parseInt(txtQuantity.getText());

                try (Connection conn = DBConnection.getConnection()) {
                    String sql = "SELECT name, price FROM menu_items WHERE item_id = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1, itemId);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        String name = rs.getString("name");
                        double price = rs.getDouble("price");

                        orderItems.add(new OrderItem(itemId, quantity, price));
                        totalAmount += price * quantity;

                        orderSummary.append("- " + name + " (" + quantity + " × ₹" + price + ")\n");
                        lblStatus.setText("Total so far: ₹" + totalAmount);
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid Item ID");
                    }
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
            }
        });

        // Submit Order
        btnSubmitOrder.addActionListener(e -> {
            String customerName = txtCustomerName.getText().trim();

            if (customerName.isEmpty() || orderItems.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Customer name and at least one item required.");
                return;
            }

            double totalAmount = orderItems.stream().mapToDouble(i -> i.price * i.quantity).sum();

            try (Connection conn = DBConnection.getConnection()) {
                // 1. Insert order
                String orderSql = "INSERT INTO orders (customer_name, total_amount) VALUES (?, ?)";
                PreparedStatement orderStmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
                orderStmt.setString(1, customerName);
                orderStmt.setDouble(2, totalAmount);
                orderStmt.executeUpdate();

                ResultSet generatedKeys = orderStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);

                    // 2. Insert each item into order_items
                    String itemSql = "INSERT INTO order_items (order_id, item_id, quantity) VALUES (?, ?, ?)";
                    PreparedStatement itemStmt = conn.prepareStatement(itemSql);

                    for (OrderItem item : orderItems) {
                        itemStmt.setInt(1, orderId);
                        itemStmt.setInt(2, item.itemId);
                        itemStmt.setInt(3, item.quantity);
                        itemStmt.addBatch();
                    }

                    itemStmt.executeBatch();

                    JOptionPane.showMessageDialog(this,
                            "✅ Order placed successfully!\n🧾 Total Amount: ₹" + String.format("%.2f", totalAmount));

                    // 3. Reset fields
                    orderItems.clear();
                    txtCustomerName.setText("");
                    txtItemId.setText("");
                    txtQuantity.setText("");
                    orderSummary.setText("");

                } else {
                    JOptionPane.showMessageDialog(this, "❌ Failed to create order record.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "❌ Error while placing order.");
            }
        });


        // Clear
        btnClear.addActionListener(e -> {
            txtCustomerName.setText("");
            txtItemId.setText("");
            txtQuantity.setText("");
            orderSummary.setText("");
            lblStatus.setText("Status: Ready");
            orderItems.clear();
            totalAmount = 0.0;
        });

        setVisible(true);
    }

    class OrderItem {
        int itemId;
        int quantity;
        double price;

        OrderItem(int itemId, int quantity, double price) {
            this.itemId = itemId;
            this.quantity = quantity;
            this.price = price;
        }
    }
}
