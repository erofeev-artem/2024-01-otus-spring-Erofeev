package ru.otus.hw.commands;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.hw.CommandsUtils.*;

@DisplayName("Команды для работы с книгами")
@SpringBootTest
public class BookCommandsTest {
    @Autowired
    private BookCommands bookCommands;
    @Autowired
    private AuthorCommands authorCommands;
    @Autowired
    private GenreCommands genreCommands;
    @Autowired
    private BookConverter bookConverter;

    @DisplayName("Получаем список всех книг")
    @Test
    public void findAllBooks() {
        String actualBooks = bookCommands.findAllBooks();
        assertThat(actualBooks).contains("Fahrenheit 451");
        assertThat(actualBooks).contains("Atlas shrugged");
        assertThat(actualBooks).contains("Starship troopers");
    }

    @DisplayName("Получаем книги по id")
    @Test
    public void findBookById() {
        String expectedBook = bookCommands.findBookByTitle("Atlas shrugged");
        var actualBook = bookCommands.findBookById(getIdFromBook(expectedBook));
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("Добавляем новую книгу")
    @Test
    public void insertBook() {
        String author = authorCommands.findAuthorByName("Author_1");
        String genre1 = genreCommands.findGenreByName("Genre_1");
        String genre2 = genreCommands.findGenreByName("Genre_3");
        String expectedBook = bookCommands.insertBook("Test title", getIdFromAuthor(author),
                Set.of(getIdFromGenre(genre1), getIdFromGenre(genre2)));
        String actualBook = bookCommands.findBookByTitle("Test title");
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("Изменяем существующую книгу")
    @Test
    public void updateBook() {
        String expectedBook = bookCommands.updateBook("3", "Starship troopers", "2", Set.of("2", "3"));
        String actualBook = bookCommands.findBookById("3");
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("Удаляем существующую книгу")
    @Test
    public void deleteBook() {
        bookCommands.deleteBook("2");
        String result = bookCommands.findBookById("2");
        assertThat(result).isEqualTo("Book with id 2 not found");
    }
}
