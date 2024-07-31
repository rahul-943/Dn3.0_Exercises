package E_commerce_platform;

import java.util.Arrays;
import java.util.Scanner;

class Product {
    private String productId;
    private String productName;
    private String category;

    public Product(String productId, String productName, String category) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }

    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getCategory() { return category; }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}

public class ECommercePlatformSearch {
    public static Product linearSearch(Product[] products, String productName) {
        for (Product product : products) {
            if (product.getProductName().equalsIgnoreCase(productName)) {
                return product;
            }
        }
        return null;
    }

    public static Product binarySearch(Product[] products, String productName) {
        int left = 0;
        int right = products.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = products[mid].getProductName().compareToIgnoreCase(productName);

            if (comparison == 0) {
                return products[mid];
            }
            if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // Initializing products
        Product[] products = {
                new Product("1", "Laptop", "Electronics"),
                new Product("2", "Smartphone", "Electronics"),
                new Product("3", "Desk", "Furniture"),
                new Product("4", "Chair", "Furniture"),
                new Product("5", "Headphones", "Electronics")
        };

        // Sorting products for binary search
        Arrays.sort(products, (a, b) -> a.getProductName().compareToIgnoreCase(b.getProductName()));

        // Creating a Scanner object for user input
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose search method:");
            System.out.println("1. Linear Search");
            System.out.println("2. Binary Search");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 3) {
                System.out.println("Exiting...");
                break;
            }

            System.out.print("Enter product name to search: ");
            String productName = scanner.nextLine();

            Product foundProduct = null;
            switch (choice) {
                case 1:
                    foundProduct = linearSearch(products, productName);
                    break;
                case 2:
                    foundProduct = binarySearch(products, productName);
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
                    continue;
            }

            System.out.println(foundProduct != null ? foundProduct : "Product not found.");
        }

        scanner.close();
    }
}

