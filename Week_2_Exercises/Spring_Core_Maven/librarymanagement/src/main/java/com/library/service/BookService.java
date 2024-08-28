// Exercise 1 - 5
// package com.library.service;

// import com.library.repository.BookRepository;

// public class BookService {
//     private BookRepository bookRepository;

//     public void setBookRepository(BookRepository bookRepository) {
//         this.bookRepository = bookRepository;
//     }

//     public void manageBooks() {
//         // Logic to manage books
//         System.out.println("Managing books...");
//     }
// }
//exercise->6
// package com.library.service;
// import com.library.repository.BookRepository;
// import org.springframework.stereotype.Service;

// @Service
// public class BookService {
    
//     private final BookRepository bookRepository;

//     // Constructor injection (recommended)
//     public BookService(BookRepository bookRepository) {
//         this.bookRepository = bookRepository;
//     }

//     public void manageBooks() {
//         // Logic to manage books
//         System.out.println("Managing books...");
//         // You can use bookRepository here for any database operations
//     }
// }

//exercise->7
package com.library.service;

import com.library.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private BookRepository bookRepository;

    // Constructor injection
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Setter injection
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void manageBooks() {
        System.out.println("Managing books...");
    }
}


