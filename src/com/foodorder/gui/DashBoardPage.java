package com.foodorder.gui;
import com.foodorder.MenuManager;
import com.foodorder.gui.UpdateMenuFrame;
import com.foodorder.gui.ViewMenuFrame;
import com.foodorder.gui.UpdateMenuFrame;
import com.foodorder.DBConnection;
import com.foodorder.gui.DeleteMenuItemFrame;


import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DashBoardPage extends JFrame {

    public DashBoardPage() {
        setTitle("Admin Dashboard - Food Ordering System");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1, 10, 10));

        JButton btnAddMenu = new JButton("➕ Add Menu Item");
        JButton btnUpdateMenu = new JButton("📝 Update Menu Item");
        JButton btnDeleteMenu = new JButton("❌ Delete Menu Item");
        JButton btnViewMenu = new JButton("📋 View Menu");
        JButton btnPlaceOrder = new JButton("🍔Place Order");
        JButton btnViewOrders = new JButton("🍽️ View Food Orders");
        JButton btnExport = new JButton("📤 Export Orders");
        JButton btnExit = new JButton("🚪 Exit");

        panel.add(btnAddMenu);
        panel.add(btnUpdateMenu);   
        panel.add(btnDeleteMenu);
        panel.add(btnViewMenu);
        panel.add(btnPlaceOrder);
        panel.add(btnViewOrders);
        panel.add(btnExport);
        panel.add(btnExit);

        add(panel);

        // ACTION LISTENER  (hook methods to buttons)
        btnAddMenu.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Enter Item Name:");
            String priceStr = JOptionPane.showInputDialog(this, "Enter Price:");

            if (name != null && priceStr != null) {
                try {
                    double price = Double.parseDouble(priceStr);
                    MenuManager menuManager = new MenuManager();
                    menuManager.addMenuItem(name, price);
                    JOptionPane.showMessageDialog(this, "✅ Item added successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "❌ Invalid price format!");
                }
            }
        });



        btnUpdateMenu.addActionListener(e -> new UpdateMenuFrame());
        btnDeleteMenu.addActionListener(e -> new DeleteMenuItemFrame());
        btnViewMenu.addActionListener(e -> new ViewMenuFrame());
        btnPlaceOrder.addActionListener(e -> new PlaceOrderFrame());
        btnViewOrders.addActionListener(e -> new ViewOrdersFrame());;
        btnExport.addActionListener(e-> new ExportOrdersFrame());
        btnExit.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    
}
