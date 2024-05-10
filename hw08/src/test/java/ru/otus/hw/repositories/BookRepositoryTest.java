package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.Hw08Application;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Set;

@DataMongoTest
@DisplayName("Репозиторий для работы с книгами")
@Import(Hw08Application.class)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    GenreRepository genreRepository;

    @DisplayName("Должен удалять книгу по названию")
    @Test
    void shouldDeleteBookByTitle() {
        bookRepository.deleteBookByTitle("Starship troopers");
        Assertions.assertTrue(bookRepository.findByTitle("Starship troopers").isEmpty());
    }

    @DisplayName("Должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        Author author = authorRepository.findAll().get(0);
        List<Genre> genres = genreRepository.findAll();
        var newBook = new Book("Star Wars", author, Set.of(genres.get(2), genres.get(0)));
        bookRepository.save(newBook);
        Assertions.assertTrue(bookRepository.findByTitle("Star Wars").isPresent());
    }

    @Test
    @DisplayName("Должен загружать книгу по названию")
    void shouldReturnCorrectBookByTitle() {
        Assertions.assertTrue(bookRepository.findByTitle("Starship troopers").isPresent());
    }
}