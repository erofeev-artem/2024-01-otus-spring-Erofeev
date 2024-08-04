package ru.otus.order_processing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.order_processing.model.Client;
import ru.otus.order_processing.model.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Client> findByFirstNameAndMiddleNameAndLastNameAndPhoneNumber(String firstName, String middleName,
                                                                           String lastName, String phoneNumber);
}
