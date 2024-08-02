package ru.otus.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.order.model.ClientAddress;

import java.util.Optional;

public interface ClientAddressRepository extends JpaRepository<ClientAddress, Long> {
    Optional<ClientAddress> findByAllowedAddressIdAndApartment
            (long allowedAddressId, String apartment);
}
