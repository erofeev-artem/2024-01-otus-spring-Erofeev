package ru.otus.order_processing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.order_processing.model.ConnectionAddress;

import java.util.Optional;

public interface ConnectionAddressRepository extends JpaRepository<ConnectionAddress, Long> {

    Optional<ConnectionAddress> findByCityAndStreetAndHouseAndBuildingAndStructure(String city,
                                                                                   String street,
                                                                                   String house,
                                                                                   String building,
                                                                                   String structure);
}
