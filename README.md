# 🍽️ Online Food Ordering System

A console-based Food Ordering System built with **Java**, **MySQL**, and **JDBC**, designed to simulate real-world backend operations like menu management, placing orders, and viewing order history. Ideal for DBMS and backend development practice.

---

## 📌 Features Implemented

✅ Add new menu items  
✅ View all available food items  
✅ Place an order with multiple items  
✅ Automatically calculates total cost  
✅ Stores all orders and item details in database  
✅ View all placed orders with item breakdown

---

## 🧰 Tech Stack

| Technology | Usage |
|------------|-------|
| Java       | Core programming language |
| MySQL      | Backend relational database |
| JDBC       | Java-Database connectivity |
| SQL        | Querying and table management |

---

## 🗂️ Folder Structure

Project_DBMS_Food_Ordering_System/
├── src/
│ └── com/foodorder/
│ ├── Main.java
│ ├── MenuManager.java
│ └── DBConnection.java
├── lib/
│ └── mysql-connector-j-9.3.0.jar
├── bin/
└── README.md


---

## 🛠️ Setup Instructions

1. **Install MySQL Server and MySQL Workbench**  
2. **Create a database** named: `food_ordering_system`  
3. **Run the following SQL:**

```sql
CREATE TABLE menu_items (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    price DECIMAL(10, 2)
);

CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100),
    order_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2)
);

CREATE TABLE order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    item_id INT,
    quantity INT,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (item_id) REFERENCES menu_items(item_id)
);

##Compile the code-
javac -cp ".;lib/mysql-connector-j-9.3.0.jar" -d bin src/com/foodorder/*.java

##Run the Prog:
java -cp ".;bin;lib/mysql-connector-j-9.3.0.jar" com.foodorder.Main


👨‍💻 Author

Dhanush G M
B.E Computer Science
RVITM, Bengaluru
