package ru.otus.hw.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.rest.CommentRestController;
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.CommentService;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentRestController.class)
@Import(SecurityConfiguration.class)
public class CommentRestControllerAuthenticationTest {
    @Autowired


    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @WithMockUser(username = "administrator")
    @DisplayName("Проверка доступа к сохранению комментария для пользователя administrator")
    @Test
    public void saveAuthenticatedAsAdministrator() throws Exception {
        Map<String, String> commentData = Map.of("text", "awesome", "bookId", "17");
        mockMvc.perform(post("/comments").content(new ObjectMapper().writeValueAsString(commentData))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    @DisplayName("Проверка доступа к сохранению комментария без аутентификации")
    @Test
    public void saveUnauthenticated() throws Exception {
        Map<String, String> commentData = Map.of("text", "awesome", "bookId", "17");
        mockMvc.perform(post("/comments").content(new ObjectMapper().writeValueAsString(commentData))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isFound());
    }

    @DisplayName("Получение комментария по id книги для пользователя user")
    @WithMockUser(username = "user")
    @Test
    void commentsByBookIdAuthenticatedAsUser() throws Exception {
        Author author = new Author(124L, "Author1");
        Genre genre = new Genre(11, "Genre1");
        Book book = new Book(1024, "Boring book", author, List.of(genre));
        Comment comment = new Comment(45, "Could have been better", book);
        when(commentService.findByBookId(1024)).thenReturn(List.of(comment));
        this.mockMvc.perform(get("/comments/book/1024")
                        .queryParam("bookId", "1024"))
                .andExpect(status().isOk());
    }

    @DisplayName("Получение комментария по id книги без аутентификации")
    @Test
    void commentsByBookIdUnauthenticated() throws Exception {
        Author author = new Author(124L, "Author1");
        Genre genre = new Genre(11, "Genre1");
        Book book = new Book(1024, "Boring book", author, List.of(genre));
        Comment comment = new Comment(45, "Could have been better", book);
        when(commentService.findByBookId(1024)).thenReturn(List.of(comment));
        this.mockMvc.perform(get("/comments/book/1024")
                        .queryParam("bookId", "1024"))
                .andExpect(status().isFound());
    }
}
