package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.dto.BookDTO;
import com.example.bookstoreapi.mapper.BookMapper;
import com.example.bookstoreapi.repository.BookRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "Operations related to books in the bookstore")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Create a new book", description = "Add a new book to the bookstore")
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        book = bookRepository.save(book);
        BookDTO responseDTO = bookMapper.toDTO(book);
        addLinks(responseDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a book by ID", description = "Retrieve a book by its ID")
    public ResponseEntity<BookDTO> getBook(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDTO)
                .map(this::addLinks)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieve a list of all books")
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDTO)
                .peek(this::addLinks)
                .toList();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a book", description = "Update the details of an existing book")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    Book book = bookMapper.toEntity(bookDTO);
                    book.setId(existingBook.getId());
                    book.setVersion(existingBook.getVersion());
                    book = bookRepository.save(book);
                    BookDTO responseDTO = bookMapper.toDTO(book);
                    addLinks(responseDTO);
                    return ResponseEntity.ok(responseDTO);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book", description = "Remove a book from the bookstore")
    public ResponseEntity<Object> deleteBook(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    bookRepository.delete(book);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private BookDTO addLinks(BookDTO bookDTO) {
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBook(bookDTO.getId())).withSelfRel();
        Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).updateBook(bookDTO.getId(), bookDTO)).withRel("update");
        Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).deleteBook(bookDTO.getId())).withRel("delete");
        
        bookDTO.add(selfLink);
        bookDTO.add(updateLink);
        bookDTO.add(deleteLink);
        return bookDTO;
    }
}
