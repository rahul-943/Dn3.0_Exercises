package Sorting_Customer_Orders;

import java.util.Arrays;
import java.util.Scanner;

public class SortingCustomerOrders {

    // Order class
    public static class Order {
        private String orderId;
        private String customerName;
        private double totalPrice;

        public Order(String orderId, String customerName, double totalPrice) {
            this.orderId = orderId;
            this.customerName = customerName;
            this.totalPrice = totalPrice;
        }

        public String getOrderId() { return orderId; }
        public String getCustomerName() { return customerName; }
        public double getTotalPrice() { return totalPrice; }

        @Override
        public String toString() {
            return "Order{" +
                    "orderId='" + orderId + '\'' +
                    ", customerName='" + customerName + '\'' +
                    ", totalPrice=" + totalPrice +
                    '}';
        }
    }

    // Bubble Sort
    public static class BubbleSort {
        public static void bubbleSort(Order[] orders) {
            int n = orders.length;
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (orders[j].getTotalPrice() > orders[j + 1].getTotalPrice()) {
                        // Swap orders[j] and orders[j + 1]
                        Order temp = orders[j];
                        orders[j] = orders[j + 1];
                        orders[j + 1] = temp;
                    }
                }
            }
        }
    }

    // Quick Sort
    public static class QuickSort {
        public static void quickSort(Order[] orders, int low, int high) {
            if (low < high) {
                int pi = partition(orders, low, high);

                quickSort(orders, low, pi - 1);
                quickSort(orders, pi + 1, high);
            }
        }

        private static int partition(Order[] orders, int low, int high) {
            double pivot = orders[high].getTotalPrice();
            int i = (low - 1);

            for (int j = low; j < high; j++) {
                if (orders[j].getTotalPrice() <= pivot) {
                    i++;
                    // Swap orders[i] and orders[j]
                    Order temp = orders[i];
                    orders[i] = orders[j];
                    orders[j] = temp;
                }
            }

            // Swap orders[i + 1] and orders[high] (or pivot)
            Order temp = orders[i + 1];
            orders[i + 1] = orders[high];
            orders[high] = temp;

            return i + 1;
        }
    }

    // Main method to handle user input and sorting
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of orders: ");
        int numOrders = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Order[] orders = new Order[numOrders];

        for (int i = 0; i < numOrders; i++) {
            System.out.print("Enter order ID for order " + (i + 1) + ": ");
            String orderId = scanner.nextLine();
            System.out.print("Enter customer name for order " + (i + 1) + ": ");
            String customerName = scanner.nextLine();
            System.out.print("Enter total price for order " + (i + 1) + ": ");
            double totalPrice = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            orders[i] = new Order(orderId, customerName, totalPrice);
        }

        System.out.println("\nChoose sorting method:");
        System.out.println("1. Bubble Sort");
        System.out.println("2. Quick Sort");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        Order[] sortedOrders = Arrays.copyOf(orders, orders.length);

        switch (choice) {
            case 1:
                BubbleSort.bubbleSort(sortedOrders);
                System.out.println("\nOrders sorted by Bubble Sort:");
                break;
            case 2:
                QuickSort.quickSort(sortedOrders, 0, sortedOrders.length - 1);
                System.out.println("\nOrders sorted by Quick Sort:");
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        for (Order order : sortedOrders) {
            System.out.println(order);
        }

        scanner.close();
    }
}

