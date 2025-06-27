📦 Online Food Ordering System (Java + MySQL + Swing GUI)

A simple, fully functional food ordering system with both GUI and CLI support. Built using Java, MySQL, and Java Swing, this project serves as a great DBMS mini project with all core CRUD operations and export capabilities.


📁 Project Structure

Project_DBMS_Food_Ordering_System/
├── bin/                            # Compiled .class files
├── lib/                            # MySQL JDBC connector (JAR)
├── orders_export.csv               # Auto-generated order export file
├── src/
│   ├── com/
│   │   └── foodorder/
│   │       ├── App.java                        # Entry point
│   │       ├── DBConnection.java               # JDBC connection handler
│   │       ├── MenuManager.java                # CLI menu management
│   │       ├── OrderManager.java               # CLI order placing logic
│   │       └── Main.java                       # Terminal admin login
│   │
│   │   └── gui/
│   │       ├── LoginFrame.java                 # Admin login (GUI)
│   │       ├── DashBoardPage.java              # Admin dashboard
│   │       ├── AddMenuItemFrame.java           # Add new item
│   │       ├── UpdateMenuFrame.java            # Update item details
│   │       ├── DeleteMenuItemFrame.java        # Delete item (with FK constraint handled)
│   │       ├── ViewMenuFrame.java              # View full menu
│   │       ├── ViewOrdersFrame.java            # View food orders
│   │       ├── ExportOrders.java               # Export orders as CSV
│   │       └── PlaceOrderFrame.java            # GUI-based order placement


✅ Features Overview

| Feature              | Terminal Version   | GUI Version          |
| -------------------- | ----------------   | -------------------  |
| Admin Login          | ✅                | ✅                   |
| Add Menu Item        | ✅                | ✅                   |
| Update Menu Item     | ✅                | ✅                   |
| Delete Menu Item     | ✅                | ✅                   |
| View Menu            | ✅                | ✅                   |
| Place Order          | ✅                | ✅                   |
| View Food Orders     | ✅                | ✅                   |
| Export Orders to CSV | ✅                | ✅                   |


🛠 Setup Instructions

1. Install MySQL and run:
CREATE DATABASE food_ordering_system;

-- Create tables manually or import schema if available.

2. Download & Add MySQL Connector:
Place mysql-connector-j-9.x.x.jar inside the lib/ folder.

3. Compile All Classes:
javac -cp ".;lib/mysql-connector-j-9.3.0.jar" -d bin src/com/foodorder/*.java src/com/foodorder/gui/*.java

4. Run the GUI App:
java -cp ".;bin;lib/mysql-connector-j-9.3.0.jar" com.foodorder.App

5. Run the Terminal (CLI) App: 
java -cp ".;bin;lib/mysql-connector-j-9.3.0.jar" com.foodorder.Main


👤 Admin Credentials:
Terminal: Username- admin 
          Password- password

APP/GUI: Username- admin
         Password- pass

You can modify these in LoginFrame.java and Main.java.


📤 Export Format (CSV):

File: orders_export.csv
OrderID,Customer,Item,Quantity,Price,Total
101,Harish,Curd,3,200.0,600.0
101,Harish,Rice and Sambar,2,300.0,600.0


🧪 Sample Menu Data:

INSERT INTO menu_items (name, price) VALUES
('Rice and Sambar', 300),
('Curd', 200),
('Dal Roti', 100);


👨‍💻 Developed By

Dhanush G M
