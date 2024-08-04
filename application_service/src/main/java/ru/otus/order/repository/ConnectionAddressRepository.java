package ru.otus.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.order.model.ConnectionAddress;

import java.util.Optional;

public interface ConnectionAddressRepository extends JpaRepository<ConnectionAddress, Long> {
    Optional<ConnectionAddress> findByAllowedAddressIdAndApartment
            (long allowedAddressId, String apartment);
}
