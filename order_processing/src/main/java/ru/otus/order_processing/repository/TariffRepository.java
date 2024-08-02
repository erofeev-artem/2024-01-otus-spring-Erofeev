package ru.otus.order_processing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.order_processing.model.Tariff;

import java.util.List;

public interface TariffRepository extends JpaRepository<Tariff, Long> {

    List<Tariff> findByArchivedFalse();
}
