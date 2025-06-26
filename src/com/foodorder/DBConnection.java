package com.foodorder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/food_ordering_system";
    private static final String USER = "root";  // or your MySQL user
    private static final String PASSWORD = "Naruto#4849";  // put your root password here

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to the database!");
        } catch (SQLException e) {
            System.out.println("❌ Connection failed.");
            e.printStackTrace();
        }
        return conn;
    }
}
