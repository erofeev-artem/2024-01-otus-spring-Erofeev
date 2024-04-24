package ru.otus.hw.hw08new.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.hw08new.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {
    Optional<Genre> findById(String id);

    @Override
    List<Genre> findAll();
}
