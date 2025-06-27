package com.foodorder.gui;

import com.foodorder.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewMenuFrame extends JFrame {

    public ViewMenuFrame() {
        setTitle("📋 View Menu");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"ID", "Name", "Price"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT item_id, name, price FROM menu_items";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("item_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");

                model.addRow(new Object[]{id, name, String.format("₹%.2f", price)});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Failed to load menu data");
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}
