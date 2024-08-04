package ru.otus.order_processing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.order_processing.dto.CustomerDto;
import ru.otus.order_processing.mapper.CustomerMapper;
import ru.otus.order_processing.model.Customer;
import ru.otus.order_processing.repository.CustomerRepository;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    public Customer save(CustomerDto customerDto) {
        Customer customer = customerMapper.dtoToCustomer(customerDto);
        return customerRepository.save(customer);
    }
}
