package com.foodorder.gui;
import com.foodorder.DBConnection;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.View;

import java.sql.*;


public class ViewOrdersFrame extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public ViewOrdersFrame(){
        setTitle("View window title");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model=new DefaultTableModel();
        table = new JTable(model);
        JScrollPane scrollPane=new JScrollPane(table);

        model.addColumn("Order ID");
        model.addColumn("Customer Name");
        model.addColumn("Item ID");
        model.addColumn("Quantity");
        model.addColumn("Price (₹)");
        model.addColumn("Total Order Amount");

        DisplayOrders();

        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);

    }

    private void DisplayOrders() {
        try (Connection conn = DBConnection.getConnection()){
            String sqlQuery="SELECT o.order_id, o.customer_name, mi.name AS item_name, oi.quantity, mi.price, o.total_amount " +
                         "FROM orders o " +
                         "JOIN order_items oi ON o.order_id = oi.order_id " +
                         "JOIN menu_items mi ON oi.item_id = mi.item_id " +
                         "ORDER BY o.order_id DESC";

            PreparedStatement ps=conn.prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int orderId=rs.getInt("order_id");
                String customerName=rs.getString("customer_name");
                String item=rs.getString("item_name");
                int quantity=rs.getInt("quantity");
                double price=rs.getDouble("price");
                double totalAmount=rs.getDouble("total_amount");

                model.addRow(new Object[]{orderId, customerName, item, quantity, price, totalAmount});
            }
            }
            catch(SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error fetching orders: ");
            }
        }
    
}
