package ru.otus.order_processing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.order_processing.dto.CustomerDto;
import ru.otus.order_processing.mapper.CustomerMapper;
import ru.otus.order_processing.model.Customer;
import ru.otus.order_processing.repository.CustomerRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    public Customer saveOrUpdate(CustomerDto customerDto) {
        Optional<Customer> optionalCustomer = customerRepository.findByPhoneNumber(customerDto.phoneNumber());
        Customer customer;
        if (optionalCustomer.isEmpty()) {
            customer = customerMapper.dtoToCustomer(customerDto);
        } else {
            customer = optionalCustomer.get();
            customer.setFirstName(customerDto.firstName());
            customer.setMiddleName(customerDto.middleName());
            customer.setLastName(customerDto.lastName());
        }
        return customerRepository.save(customer);
    }
}
