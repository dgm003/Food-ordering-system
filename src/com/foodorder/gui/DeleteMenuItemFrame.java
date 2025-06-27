package com.foodorder.gui;

import com.foodorder.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteMenuItemFrame extends JFrame {

    public DeleteMenuItemFrame() {
        setTitle("🗑️ Delete Menu Item");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel idLabel = new JLabel("Enter Item ID to Delete:");
        JTextField idField = new JTextField();

        JButton deleteBtn = new JButton("🗑️ Delete");
        JButton cancelBtn = new JButton("🚫 Cancel");

        panel.add(idLabel);
        panel.add(idField);
        panel.add(new JLabel()); 
        panel.add(new JLabel()); 
        panel.add(deleteBtn);
        panel.add(cancelBtn);

        add(panel);

        deleteBtn.addActionListener((ActionEvent e) -> {
            String input = idField.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, " Please enter an Item ID.");
                return;
            }

            try {
                int itemId = Integer.parseInt(input);

                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete Item ID " + itemId + "?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    try (Connection conn = DBConnection.getConnection()) {
                        String sql = "DELETE FROM menu_items WHERE item_id = ?";
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setInt(1, itemId);

                        int rows = stmt.executeUpdate();
                        if (rows > 0) {
                            JOptionPane.showMessageDialog(this, "✅ Item deleted successfully!");
                        } else {
                            JOptionPane.showMessageDialog(this, "❌ Item not found.");
                        }
                    }
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❌ Invalid Item ID format.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "❌ Error while deleting item.");
                ex.printStackTrace();
            }
        });

        cancelBtn.addActionListener(e -> dispose());

        setVisible(true);
    }
}
