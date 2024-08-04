package ru.otus.order_processing.service;

import ru.otus.order_processing.dto.CustomerDto;
import ru.otus.order_processing.model.Customer;

public interface CustomerService {

    public Customer saveOrUpdate(CustomerDto customerDto);

    public Customer findByPhoneNumber(String phoneNumber);
}
