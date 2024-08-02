package ru.otus.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.order.model.AllowedAddress;

import java.util.List;
import java.util.Optional;

public interface AllowedAddressRepository extends JpaRepository<AllowedAddress, Long> {
    Optional<AllowedAddress> findByCityAndStreetAndHouseAndBuildingAndStructure
            (String city, String street, String house, String building, String structure);

    List<AllowedAddress> findByCity(String city);

    List<AllowedAddress> findByHouse(String house);

    List<AllowedAddress> findByStreet(String street);

    List<AllowedAddress> findByBuilding(String building);

    List<AllowedAddress> findByStructure(String city);

}
