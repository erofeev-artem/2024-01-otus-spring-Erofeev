package ru.otus.order_processing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.order_processing.model.Engineer;

public interface EngineerRepository extends JpaRepository<Engineer, Long> {
}
