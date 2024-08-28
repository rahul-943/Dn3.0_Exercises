package com.example.bookstoreapi.mapper;

import com.example.bookstoreapi.Customer;
import com.example.bookstoreapi.dto.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO toDto(Customer customer);
    Customer toEntity(CustomerDTO customerDTO);
}
