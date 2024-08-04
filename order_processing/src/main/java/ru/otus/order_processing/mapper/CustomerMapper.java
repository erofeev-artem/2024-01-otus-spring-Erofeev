package ru.otus.order_processing.mapper;

import org.mapstruct.Mapper;
import ru.otus.order_processing.dto.CustomerDto;
import ru.otus.order_processing.model.Customer;

@Mapper
public interface CustomerMapper {
    CustomerDto customerToDto(Customer customer);

    Customer dtoToCustomer(CustomerDto customerDto);
}
