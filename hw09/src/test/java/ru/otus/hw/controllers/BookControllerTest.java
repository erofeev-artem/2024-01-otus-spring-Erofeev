package ru.otus.hw.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controller.BookController;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.GenreService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для работы с книгами")
@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private CommentService commentService;

    private Author author1;
    private Author author2;
    private Genre genre1;
    private Genre genre2;

    @BeforeEach
    void initBookData() {
        author1 = new Author(123, "Author1");
        author2 = new Author(123, "Author2");
        genre1 = new Genre(11, "Genre1");
        genre2 = new Genre(12, "Genre2");
    }

    @DisplayName("Получение всех книг")
    @Test
    void allBooks() throws Exception {
        Book starWars = new Book(11, "StarWars", author1, List.of(genre1, genre2));
        Book fightClub = new Book(11, "Fight club", author2, List.of(genre2));
        when(bookService.findAll()).thenReturn(List.of(starWars, fightClub));
        this.mockMvc.perform(get("/book/all"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(containsString("StarWars")))
                .andExpect(content()
                        .string(containsString("Fight club")));
    }


    @DisplayName("Редактирование книги")
    @Test
    void editBook() throws Exception {
        this.mockMvc.perform(post("/book/edit")
                        .queryParam("id", "1")
                        .queryParam("title", "Old road")
                        .queryParam("authorId", String.valueOf(author2.getId()))
                        .queryParam("genresIds", String.valueOf(genre2.getId())))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/book/all"));
    }

    @DisplayName("Добавление новой книги")
    @Test
    void saveBook() throws Exception {
        this.mockMvc.perform(post("/book/create")
                        .queryParam("title", "Big city")
                        .queryParam("authorId", "2")
                        .queryParam("genresIds", "1"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/book/all"));
    }

    @DisplayName("Удаление книги")
    @Test
    void deleteBook() throws Exception {
        this.mockMvc.perform(get("/book/delete")
                        .queryParam("id", "2"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/book/all"));
    }

    @DisplayName("Изменение книги")
    @Test
    void updateBook() throws Exception {
        this.mockMvc.perform(post("/book/edit")
                        .queryParam("id", "2")
                        .queryParam("title", "Spring in action")
                        .queryParam("authorId", "2")
                        .queryParam("genresIds", "1"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/book/all"));
    }

    @DisplayName("Получение информации о книге")
    @Test
    void bookInfo() throws Exception {
        Book lordOfTheRings = new Book(2, "Lord of the rings", author1, List.of(genre1, genre2));
        when(bookService.findById(2)).thenReturn(Optional.of(lordOfTheRings));
        this.mockMvc.perform(get("/book/info")
                        .queryParam("id", "2"))
                .andExpect(status().is(200))
                .andExpect(content().string(containsString("Lord of the rings")));
    }
}
