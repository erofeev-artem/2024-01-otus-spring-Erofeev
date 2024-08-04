package ru.otus.order_processing.service;

import ru.otus.order_processing.dto.CustomerDto;
import ru.otus.order_processing.model.Customer;

public interface CustomerService {

    public Customer save(CustomerDto customerDto);
}
