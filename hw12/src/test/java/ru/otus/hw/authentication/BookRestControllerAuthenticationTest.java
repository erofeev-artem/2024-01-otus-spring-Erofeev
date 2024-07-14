package ru.otus.hw.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookRestController.class)
@Import(SecurityConfiguration.class)
public class BookRestControllerAuthenticationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    private static Author author1;

    private static Author author2;

    private static Genre genre1;

    private static Genre genre2;

    private static BookDto bookDto;

    private static BookDto bookDtoWithoutId;

    private static Book book;

    static {
        author1 = new Author(123L, "Author1");
        author2 = new Author(124L, "Author2");
        genre1 = new Genre(11, "Genre1");
        genre2 = new Genre(12, "Genre2");
        bookDto = new BookDto(2, "Jurassic park", "Author1", List.of("Genre1", "Genre2"));
        bookDtoWithoutId = new BookDto(0, "Red line", "Author1", List.of("Genre1", "Genre2"));
        book = new Book(1, "Lord of the rings", author2, List.of(genre1, genre2));
    }

    static List<Object[]> authenticatedParameters() throws Exception {
        ArrayList<Object[]> parameters = new ArrayList<>();
        parameters.add(new Object[]{MockMvcRequestBuilders.get("/books"), status().isOk()});
        parameters.add(new Object[]{MockMvcRequestBuilders.get("/books/1"), status().isOk()});
        parameters.add(new Object[]{MockMvcRequestBuilders.delete("/books/2"), status().isNoContent()});
        parameters.add(new Object[]{MockMvcRequestBuilders.post("/books")
                .content(new ObjectMapper().writeValueAsString(bookDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON), status().isOk()});
        parameters.add(new Object[]{MockMvcRequestBuilders.post("/books")
                .content(new ObjectMapper().writeValueAsString(bookDtoWithoutId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON), status().isCreated()});
        return parameters;
    }

    static List<RequestBuilder> unauthenticatedParameters() throws Exception {
        ArrayList<RequestBuilder> parameters = new ArrayList<>();
        parameters.add(MockMvcRequestBuilders.get("/books"));
        parameters.add(MockMvcRequestBuilders.get("/books/1"));
        parameters.add(MockMvcRequestBuilders.delete("/books/2"));
        parameters.add(MockMvcRequestBuilders.post("/books")
                .content(new ObjectMapper().writeValueAsString(bookDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        parameters.add(MockMvcRequestBuilders.post("/books")
                .content(new ObjectMapper().writeValueAsString(bookDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        parameters.add(MockMvcRequestBuilders.post("/books")
                .content(new ObjectMapper().writeValueAsString(bookDtoWithoutId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        return parameters;
    }

    @BeforeEach
    void initBookData() {
        when(bookService.findById(1)).thenReturn(book);
        when(authorService.findByFullName("Author1")).thenReturn(author1);
        when(genreService.findByGenreName(List.of("Genre1", "Genre2"))).thenReturn(List.of(genre1, genre2));
    }

    @WithMockUser(username = "administrator")
    @DisplayName("Проверка доступа для пользователя administrator")
    @ParameterizedTest
    @MethodSource("authenticatedParameters")
    public void authenticateAsAdministrator(RequestBuilder requestBuilder, ResultMatcher resultMatcher) throws Exception {
        mockMvc.perform(requestBuilder).andExpect(resultMatcher);
    }

    @DisplayName("Проверка доступа без аутентификации")
    @ParameterizedTest
    @MethodSource("unauthenticatedParameters")
    public void unauthenticatedUser(RequestBuilder requestBuilder) throws Exception {
        mockMvc.perform(requestBuilder).andExpect(status().isFound());
    }
}
