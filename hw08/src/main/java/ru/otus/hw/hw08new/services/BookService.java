package ru.otus.hw.hw08new.services;

import ru.otus.hw.hw08new.models.Book;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookService {
    Optional<Book> findById(String id);

    List<Book> findAll();

    Book insert(String title, String authorId, Set<String> genresId);

    Book update(String id, String title, String authorId, Set<String> genresId);

    void deleteById(String id);
}
