package com.example.bookstoreapi;

import com.example.bookstoreapi.model.Book;
import com.example.bookstoreapi.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        // Clear the database before each test
        bookRepository.deleteAll();
    }

    @Test
    public void testCreateBook() throws Exception {
        String bookJson = "{\"title\":\"New Book\", \"author\":\"New Author\"}";

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("New Book"));

        // Verify that the book was saved in the database
        List<Book> books = bookRepository.findAll();
        assert books.size() == 1;
        assert books.get(0).getTitle().equals("New Book");
    }

    @Test
    public void testGetAllBooks() throws Exception {
        // Add a book to the database
        Book book = new Book(1L, "Book One", "Author One");
        bookRepository.save(book);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Book One"));
    }

    @Test
    public void testGetBookById() throws Exception {
        // Add a book to the database
        Book book = new Book(1L, "Book One", "Author One");
        bookRepository.save(book);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Book One"));
    }

    @Test
    public void testUpdateBook() throws Exception {
        // Add a book to the database
        Book book = new Book(1L, "Book One", "Author One");
        bookRepository.save(book);

        String updatedBookJson = "{\"title\":\"Updated Book\", \"author\":\"Updated Author\"}";

        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedBookJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Updated Book"));

        // Verify that the book was updated in the database
        Book updatedBook = bookRepository.findById(1L).orElse(null);
        assert updatedBook != null;
        assert updatedBook.getTitle().equals("Updated Book");
    }

    @Test
    public void testDeleteBook() throws Exception {
        // Add a book to the database
        Book book = new Book(1L, "Book One", "Author One");
        bookRepository.save(book);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());

        // Verify that the book was deleted from the database
        assert bookRepository.findById(1L).isEmpty();
    }
}
