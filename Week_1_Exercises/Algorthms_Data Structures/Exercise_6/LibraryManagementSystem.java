package Library_Management_System;

import java.util.Arrays;
import java.util.Scanner;

public class LibraryManagementSystem {

    // Book class
    public static class Book {
        private String bookId;
        private String title;
        private String author;

        public Book(String bookId, String title, String author) {
            this.bookId = bookId;
            this.title = title;
            this.author = author;
        }

        public String getBookId() { return bookId; }
        public String getTitle() { return title; }
        public String getAuthor() { return author; }

        @Override
        public String toString() {
            return "Book{" +
                    "bookId='" + bookId + '\'' +
                    ", title='" + title + '\'' +
                    ", author='" + author + '\'' +
                    '}';
        }
    }

    // Linear Search Implementation
    public static Book linearSearchByTitle(Book[] books, String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    // Binary Search Implementation
    public static Book binarySearchByTitle(Book[] books, String title) {
        int left = 0;
        int right = books.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = books[mid].getTitle().compareToIgnoreCase(title);

            if (comparison == 0) {
                return books[mid];
            }
            if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    // Main method to handle user input
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Sample books for demonstration
        Book[] books = {
                new Book("1", "The Great Gatsby", "F. Scott Fitzgerald"),
                new Book("2", "1984", "George Orwell"),
                new Book("3", "To Kill a Mockingbird", "Harper Lee"),
                new Book("4", "Moby Dick", "Herman Melville")
        };

        // Sort books by title for binary search
        Arrays.sort(books, (b1, b2) -> b1.getTitle().compareToIgnoreCase(b2.getTitle()));

        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Search Book by Title (Linear Search)");
            System.out.println("2. Search Book by Title (Binary Search)");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter book title to search: ");
                    String title = scanner.nextLine();
                    Book bookLinear = linearSearchByTitle(books, title);
                    if (bookLinear != null) {
                        System.out.println("Book found: " + bookLinear);
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;

                case 2:
                    System.out.print("Enter book title to search: ");
                    title = scanner.nextLine();
                    Book bookBinary = binarySearchByTitle(books, title);
                    if (bookBinary != null) {
                        System.out.println("Book found: " + bookBinary);
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;

                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}

