package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.hw.models.Genre;

import java.util.List;

@DataMongoTest
@DisplayName("Репозиторий для работы с жанрами")
public class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("Получение всех жанров")
    @Test
    void findAllGenres() {
        List<Genre> all = genreRepository.findAll();
        Assertions.assertEquals(3, all.size());
    }

    @DisplayName("Получение нескольких жанров по названию")
    @Test
    void findGenresByName() {
        Genre testGenre = genreRepository.save(new Genre("Test genre"));
        Genre genre = genreRepository.findByName("Test genre").get();
        Assertions.assertEquals(testGenre.getId(), genre.getId());
    }
}