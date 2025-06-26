package com.foodorder;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); 
        MenuManager manager = new MenuManager(scanner); 

        while (true) {
            System.out.println("\n=== Online Food Ordering ===");
            System.out.println("1. Add Menu Item");
            System.out.println("2. View Menu");
            System.out.println("3. Place Order");
            System.out.println("4. View Orders");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        manager.addMenuItem();
                        break;
                    case 2:
                        manager.viewMenu();
                        break;
                    case 3:
                        manager.placeOrder();
                        break;
                    case 4:
                        manager.viewOrders();
                        break;    
                    case 5:
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
