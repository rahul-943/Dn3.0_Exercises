package Inventory_manage_sys;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Product {
    private String productId;
    private String productName;
    private int quantity;
    private double price;

    public Product(String productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}

public class Inventory {
    private Map<String, Product> productMap;

    public Inventory() {
        productMap = new HashMap<>();
    }

    // Method to add a product
    public void addProduct(Product product) {
        productMap.put(product.getProductId(), product);
    }

    // Method to update a product
    public void updateProduct(Product product) {
        if (productMap.containsKey(product.getProductId())) {
            productMap.put(product.getProductId(), product);
        } else {
            System.out.println("Product not found.");
        }
    }

    // Method to delete a product
    public void deleteProduct(String productId) {
        if (productMap.containsKey(productId)) {
            productMap.remove(productId);
        } else {
            System.out.println("Product not found.");
        }
    }

    // Method to get a product (used in the update operation)
    public Product getProduct(String productId) {
        return productMap.get(productId);
    }

    // Main method for user interaction
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Inventory Management System");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    System.out.print("Enter Product ID: ");
                    String addId = scanner.nextLine();
                    System.out.print("Enter Product Name: ");
                    String addName = scanner.nextLine();
                    System.out.print("Enter Quantity: ");
                    int addQuantity = scanner.nextInt();
                    System.out.print("Enter Price: ");
                    double addPrice = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    Product newProduct = new Product(addId, addName, addQuantity, addPrice);
                    inventory.addProduct(newProduct);
                    System.out.println("Product added successfully!");
                    break;

                case 2:
                    System.out.print("Enter Product ID to update: ");
                    String updateId = scanner.nextLine();
                    Product existingProduct = inventory.getProduct(updateId);
                    if (existingProduct != null) {
                        System.out.print("Enter new Product Name: ");
                        String updateName = scanner.nextLine();
                        System.out.print("Enter new Quantity: ");
                        int updateQuantity = scanner.nextInt();
                        System.out.print("Enter new Price: ");
                        double updatePrice = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                        existingProduct.setProductName(updateName);
                        existingProduct.setQuantity(updateQuantity);
                        existingProduct.setPrice(updatePrice);
                        inventory.updateProduct(existingProduct);
                        System.out.println("Product updated successfully!");
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter Product ID to delete: ");
                    String deleteId = scanner.nextLine();
                    inventory.deleteProduct(deleteId);
                    System.out.println("Product deleted successfully!");
                    break;

                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println();
        }
    }
}

