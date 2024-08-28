package com.example.bookstoreapi.mapper;

import com.example.bookstoreapi.Book;
import com.example.bookstoreapi.dto.BookDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO toDto(Book book);
    Book toEntity(BookDTO bookDTO);
}
