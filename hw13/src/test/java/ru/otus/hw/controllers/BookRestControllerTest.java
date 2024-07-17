package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.rest.BookRestController;
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер для работы с книгами")
@WebMvcTest(BookRestController.class)
@Import(SecurityConfiguration.class)
public class BookRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;

    private Author author1;
    private Author author2;
    private Genre genre1;
    private Genre genre2;

    @BeforeEach
    void initBookData() {
        author1 = new Author(123L, "Author1");
        author2 = new Author(124L, "Author2");
        genre1 = new Genre(11, "Genre1");
        genre2 = new Genre(12, "Genre2");
    }

    static List<Object[]> authenticationParameters() {
        ArrayList<Object[]> parameters = new ArrayList<>();
        parameters.add(new Object[]{"administrator", "admin", status().isCreated(), "Author_1"});
        parameters.add(new Object[]{"user", "user", status().isForbidden(), "Access denied"});
        return parameters;
    }

    @DisplayName("Получение всех книг с ролями admin и user")
    @ParameterizedTest
    @MethodSource("authenticationParameters")
    void allBooks(String username, String role) throws Exception {
        Book starWars = new Book(11, "StarWars", author1, List.of(genre1, genre2));
        Book fightClub = new Book(11, "Fight club", author2, List.of(genre2));
        when(bookService.findAll()).thenReturn(List.of(starWars, fightClub));
        this.mockMvc.perform(get("/books")
                        .with(user(username).roles(role)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(containsString("StarWars")))
                .andExpect(content()
                        .string(containsString("Fight club")));
    }

    @DisplayName("Получение всех книг без аутентификации")
    @Test
    void allBooksUnauthenticated() throws Exception {
        Book starWars = new Book(11, "StarWars", author1, List.of(genre1, genre2));
        Book fightClub = new Book(11, "Fight club", author2, List.of(genre2));
        when(bookService.findAll()).thenReturn(List.of(starWars, fightClub));
        this.mockMvc.perform(get("/books"))
                .andExpect(status().isFound());
    }

    @DisplayName("Добавление новой книги с ролями admin и user")
    @ParameterizedTest
    @MethodSource("authenticationParameters")
    void saveNewBook(String username, String role, ResultMatcher status) throws Exception {
        when(authorService.findByFullName("Author1")).thenReturn(author1);
        when(genreService.findByGenreName(List.of("Genre1", "Genre2"))).thenReturn(List.of(genre1, genre2));
        BookDto bookDto = new BookDto(0, "Big city", "Author1", List.of("Genre1", "Genre2"));
        this.mockMvc.perform(post("/books")
                        .with(user(username).roles(role))
                        .content(new ObjectMapper().writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status);
    }

    @DisplayName("Добавление новой книги без аутентификации")
    @Test
    void saveNewBookUnauthenticated() throws Exception {
        when(authorService.findByFullName("Author1")).thenReturn(author1);
        when(genreService.findByGenreName(List.of("Genre1", "Genre2"))).thenReturn(List.of(genre1, genre2));
        BookDto bookDto = new BookDto(0, "Big city", "Author1", List.of("Genre1", "Genre2"));
        this.mockMvc.perform(post("/books")
                        .content(new ObjectMapper().writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound());
    }

    static List<Object[]> authenticationParametersUpdateBook() {
        ArrayList<Object[]> parameters = new ArrayList<>();
        parameters.add(new Object[]{"administrator", "admin", status().isOk(), "Author_1"});
        parameters.add(new Object[]{"user", "user", status().isForbidden(), "Access denied"});
        return parameters;
    }

    @DisplayName("Изменение книги с ролями admin и user")
    @ParameterizedTest
    @MethodSource("authenticationParametersUpdateBook")
    void updateBook(String username, String role, ResultMatcher status) throws Exception {
        when(authorService.findByFullName("Author2")).thenReturn(author2);
        when(genreService.findByGenreName(List.of("Genre1", "Genre2"))).thenReturn(List.of(genre1, genre2));
        BookDto bookDto = new BookDto(1, "Old town road", "Author2", List.of("Genre1", "Genre2"));
        this.mockMvc.perform(post("/books")
                        .with(user(username).roles(role))
                        .content(new ObjectMapper().writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status);
    }

    @DisplayName("Изменение книги с ролями без аутентификации")
    @Test
    void updateBookUnauthenticated() throws Exception {
        when(authorService.findByFullName("Author2")).thenReturn(author2);
        when(genreService.findByGenreName(List.of("Genre1", "Genre2"))).thenReturn(List.of(genre1, genre2));
        BookDto bookDto = new BookDto(1, "Old town road", "Author2", List.of("Genre1", "Genre2"));
        this.mockMvc.perform(post("/books")
                        .content(new ObjectMapper().writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound());
    }

    static List<Object[]> authenticationParametersDeleteBook() {
        ArrayList<Object[]> parameters = new ArrayList<>();
        parameters.add(new Object[]{"administrator", "admin", status().isNoContent()});
        parameters.add(new Object[]{"user", "user", status().isForbidden()});
        return parameters;
    }

    @DisplayName("Удаление книги с ролями admin и user")
    @ParameterizedTest
    @MethodSource("authenticationParametersDeleteBook")
    void deleteBook(String username, String role, ResultMatcher status) throws Exception {
        this.mockMvc.perform(delete("/books/22")
                        .with(user(username).roles(role))
                        .queryParam("id", "22"))
                .andExpect(status);
    }

    @DisplayName("Удаление книги с ролями без аутентификации")
    @Test
    void deleteBookUnauthenticated() throws Exception {
        this.mockMvc.perform(delete("/books/22")
                        .queryParam("id", "22"))
                .andExpect(status().isFound());
    }

    @DisplayName("Получение книги по id с ролями admin и user")
    @ParameterizedTest
    @MethodSource("authenticationParameters")
    void getBookById(String username, String role) throws Exception {
        Book lordOfTheRings = new Book(33, "Lord of the rings", author1, List.of(genre1, genre2));
        when(bookService.findById(33)).thenReturn(lordOfTheRings);
        this.mockMvc.perform(get("/books/33")
                        .with(user(username).roles(role))
                        .queryParam("id", "33"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("{\"id\":33,\"title\":\"Lord of the rings\",\"authorName\":\"Author1\"," +
                                "\"genresNames\":[\"Genre1\",\"Genre2\"]}"));
    }

    @DisplayName("Получение книги по id без аутентификации")
    @Test
    void getBookByIdUnauthenticated() throws Exception {
        Book lordOfTheRings = new Book(33, "Lord of the rings", author1, List.of(genre1, genre2));
        when(bookService.findById(33)).thenReturn(lordOfTheRings);
        this.mockMvc.perform(get("/books/33")
                        .queryParam("id", "33"))
                .andExpect(status().isFound());
    }
}