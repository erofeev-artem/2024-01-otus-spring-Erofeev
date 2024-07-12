package ru.otus.hw.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.models.Author;
import ru.otus.hw.rest.AuthorRestController;
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.AuthorService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
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

    @DisplayName("Получение всех авторов")
    @WithMockUser(username = "administrator")
    @Test
    void allAuthors() throws Exception {
        Author author1 = new Author(1, "Author_1");
        Author author2 = new Author(2, "Author_2");
        when(authorService.findAll()).thenReturn(List.of(author1, author2));
        this.mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(containsString("Author_1")))
                .andExpect(content()
                        .string(containsString("Author_2")));
    }
}
