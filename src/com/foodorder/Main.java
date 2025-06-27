package com.foodorder;

import java.util.Scanner;

public class Main {
    private static final String AdminUsername = "admin";
    private static final String AdminPassword = "password";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); 
         // Admin Login Step
        System.out.println("🔐 Admin Login Required");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (!username.equals(AdminUsername) || !password.equals(AdminPassword)) {
            System.out.println("❌ Invalid credentials. Access denied.");
            return;
        }

        System.out.println("✅ Login successful. Welcome, Admin!");

        MenuManager manager = new MenuManager(scanner); 

        while (true) {
            System.out.println("\n=== Online Food Ordering ===");
            System.out.println("1. Add Menu Item");
            System.out.println("2. Update Menu Item");
            System.out.println("3. Delete Menu Item");
            System.out.println("4. View Menu");
            System.out.println("5. Place Order");
            System.out.println("6. View Orders");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        manager.addMenuItem();
                        break;
                    case 2:
                        manager.updateMenuItems();
                        break;
                    case 3:
                        manager.deleteMenuItem();
                        break;   
                    case 4:
                        manager.viewMenu();
                        break;
                    case 5:
                        manager.placeOrder();
                        break;
                    case 6:
                        manager.viewOrders();
                        break;    
                    case 7:
                        System.out.println("Thank You for using our Food Ordering System!👋 Exiting...");
                        return;
                    default:
                        System.out.println("❌ Invalid choice");
                }
            } else {
                System.out.println(" Please enter a valid number.");
                scanner.nextLine(); 
            }
        }
    }
}
