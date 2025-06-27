package com.foodorder.gui;

import com.foodorder.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.io.FileWriter;
import java.io.IOException;

public class ExportOrdersFrame extends JFrame{
    public ExportOrdersFrame(){
        setTitle("Export Orders(to CSV)");
        setSize(300,150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton exportButton = new JButton("Export to CSV");

        JLabel lblStatus=new JLabel("Click to export orders.", SwingConstants.CENTER);

        setLayout(new BorderLayout());
        add(lblStatus, BorderLayout.NORTH);
        add(exportButton, BorderLayout.CENTER);

        exportButton.addActionListener(e -> {
            try {
                exportOrdersToCSV();
                JOptionPane.showMessageDialog(this,"Orders exported and stored as 'orders_export.csv'" );
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, " Failed to export orders.");
            }
        });
        setVisible(true);
    }

    private void exportOrdersToCSV() throws SQLException, IOException{
        try(
            Connection conn=DBConnection.getConnection();
            FileWriter writer = new FileWriter("orders_export.csv");
        ){
            writer.write("Order ID,Customer,Item,Quantity,Price,Total Order Amount\n");
            
            // SQL JOIN to get detailed orders
            String sql = "SELECT o.order_id, o.customer_name, mi.name AS item_name, oi.quantity, mi.price, o.total_amount " +
                         "FROM orders o " +
                         "JOIN order_items oi ON o.order_id = oi.order_id " +
                         "JOIN menu_items mi ON oi.item_id = mi.item_id " +
                         "ORDER BY o.order_id DESC";

            PreparedStatement ps=conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                String customer = rs.getString("customer_name");
                String item = rs.getString("item_name");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                double total = rs.getDouble("total_amount");

                writer.write(orderId + "," + customer + "," + item + "," + quantity + "," + price + "," + total + "\n");
                
            }
            writer.flush();// It Ensures all data is written to the file
        }
            

        
    }

}
