package ru.otus.hw.services;

import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookService {
    Optional<Book> findById(String id);

    Optional<Book> findByTitle(String title);

    List<Book> findAll();

    Book insert(String title, String authorId, Set<String> genresId);

    Book update(String id, String title, String authorId, Set<String> genresId);

    void deleteById(String id);
}
