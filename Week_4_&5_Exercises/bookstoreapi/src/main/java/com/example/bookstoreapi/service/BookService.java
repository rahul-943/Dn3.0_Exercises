package com.example.bookstoreapi.service;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final MeterRegistry meterRegistry;

    @Autowired
    public BookService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void createBook() {
        // Logic to create a book
        // Increment the custom metric
        meterRegistry.counter("books.created").increment();
    }
}
