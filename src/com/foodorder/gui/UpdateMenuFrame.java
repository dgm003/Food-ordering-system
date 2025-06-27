package com.foodorder.gui;

import com.foodorder.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateMenuFrame extends JFrame {

    private JTextField itemIdField, nameField, priceField;

    public UpdateMenuFrame() {
        setTitle("📝 Update Menu Item");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Item ID to Update:"));
        itemIdField = new JTextField();
        panel.add(itemIdField);

        panel.add(new JLabel("New Item Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("New Price (₹):"));
        priceField = new JTextField();
        panel.add(priceField);

        JButton updateBtn = new JButton("Update");
        panel.add(updateBtn);

        JButton cancelBtn = new JButton("Cancel");
        panel.add(cancelBtn);

        updateBtn.addActionListener(e -> updateMenuItem());
        cancelBtn.addActionListener(e -> dispose());

        add(panel);
        setVisible(true);
    }

    private void updateMenuItem() {
        int id = Integer.parseInt(itemIdField.getText().trim());
        String name = nameField.getText().trim();
        double price = Double.parseDouble(priceField.getText().trim());

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE menu_items SET name = ?, price = ? WHERE item_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, id);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "✅ Item updated successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "❌ No item found with ID " + id);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
        }
    }
}
