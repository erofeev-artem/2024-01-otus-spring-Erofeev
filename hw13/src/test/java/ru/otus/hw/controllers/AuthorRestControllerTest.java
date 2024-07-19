package ru.otus.hw.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.otus.hw.models.Author;
import ru.otus.hw.rest.AuthorRestController;
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.AuthorService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер для работы с авторами")
@WebMvcTest(AuthorRestController.class)
@Import(SecurityConfiguration.class)
public class AuthorRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    static List<Object[]> authenticationParameters() {
        ArrayList<Object[]> parameters = new ArrayList<>();
        parameters.add(new Object[]{"administrator", "admin", status().isOk(), "Author_1"});
        parameters.add(new Object[]{"user", "user", status().isForbidden(), ""});
        return parameters;
    }

    @DisplayName("Получение всех авторов с ролями admin и user")
    @ParameterizedTest
    @MethodSource("authenticationParameters")
    void allAuthors(String username, String role, ResultMatcher status, String expectedString) throws Exception {
        Author author1 = new Author(1, "Author_1");
        Author author2 = new Author(2, "Author_2");
        when(authorService.findAll()).thenReturn(List.of(author1, author2));
        this.mockMvc.perform(get("/api/authors")
                        .with(user(username).roles(role)))
                .andExpect(status)
                .andExpect(content()
                        .string(containsString(expectedString)));
    }

    @DisplayName("Получение всех авторов без аутентификации")
    @Test
    void allAuthorsUnauthenticated() throws Exception {
        Author author1 = new Author(1, "Author_1");
        Author author2 = new Author(2, "Author_2");
        when(authorService.findAll()).thenReturn(List.of(author1, author2));
        this.mockMvc.perform(get("/api/authors"))
                .andExpect(status().isFound());
    }
}
