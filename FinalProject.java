import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FinalProject {

    // Save order history to file
    public static void saveData(ArrayList<String> data, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String item : data) {
                writer.write(item);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Load order history from file
    public static ArrayList<String> loadData(String filename) {
        ArrayList<String> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return data;
    }

    // Recursive input validation
    public static double getOrderAmount(Scanner scanner) {
        String input = scanner.next();
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return getOrderAmount(scanner);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> orderHistory = loadData("orders.txt");

        double totalSales = 0;
        int orderCount = 0;

        for (String s : orderHistory) {
            totalSales += Double.parseDouble(s);
            orderCount++;
        }

        boolean running = true;

        System.out.println("Welcome to the Coffee Order Tracker!");

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Order");
            System.out.println("2. View Order Count");
            System.out.println("3. View Total Sales");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // clear input
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter order amount: $");
                    double amount = getOrderAmount(scanner);
                    totalSales += amount;
                    orderCount++;
                    orderHistory.add(String.valueOf(amount));
                    saveData(orderHistory, "orders.txt");
                    System.out.println("Order added!");
                    break;
                case 2:
                    System.out.println("Total orders: " + orderCount);
                    break;
                case 3:
                    System.out.println("Total sales: $" + totalSales);
                    break;
                case 4:
                    System.out.println("Exiting program. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        }

        scanner.close();
    }
}