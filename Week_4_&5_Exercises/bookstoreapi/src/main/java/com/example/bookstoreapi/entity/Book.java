package com.example.bookstoreapi.entity;

import lombok.Data;



import jakarta.persistence.*;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String authorName;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private Double price;

    @Version
    private Integer version; // For optimistic locking
}
