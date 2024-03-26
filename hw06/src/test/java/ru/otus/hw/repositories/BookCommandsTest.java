package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.commands.BookCommands;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Команды для работы с книгами")
@DataJpaTest
@SpringBootTest
//@Import({JpaBookRepository.class, JpaGenreRepository.class, JpaCommentRepository.class, BookCommands.class})
@Import({BookCommands.class})
public class BookCommandsTest {

    @Autowired
    private BookCommands bookCommands;
    @Autowired
    private BookConverter bookConverter;

    private List<Author> dbAuthors;

    private List<Genre> dbGenres;
    private List<Comment> dbComments;

    private List<Book> dbBooks;

    @BeforeEach
    void setUp() {
//        dbAuthors = getDbAuthors();
//        dbGenres = getDbGenres();
//        dbComments = getComments();
//        dbBooks = getDbBooks(dbAuthors, dbGenres);
    }

    @DisplayName("получаем список всех книг")
    @Test
    public void findAllBooks() {
        String actualBooks = bookCommands.findAllBooks();
//        var expectedBooks = dbBooks;
//        String collect = expectedBooks.stream().map(book -> bookConverter.bookToString(book)).collect(Collectors.joining());
//        assertThat(actualBooks).isEqualTo(collect);
    }

    @DisplayName("получаем книги по id")
    @ParameterizedTest
    @MethodSource("getDbBooks")
    public void findBookById(Book expectedBook) {
//        var actualBook = repositoryJpa.findById(2);
//        assertThat(actualBook).isPresent()
//                .get()
//                .usingRecursiveComparison()
//                .isEqualTo(expectedBook);
    }

    public void insertBook(String title, long authorId, Set<Long> genresIds) {

    }

    public void updateBook(long id, String title, long authorId, Set<Long> genresIds) {

    }

    public void deleteBook(long id) {

    }

//    private static List<Author> getDbAuthors() {
//        return IntStream.range(1, 4).boxed()
//                .map(id -> new Author(id, "Author_" + id))
//                .toList();
//    }
//
//    private static List<Genre> getDbGenres() {
//        return IntStream.range(1, 7).boxed()
//                .map(id -> new Genre(id, "Genre_" + id))
//                .toList();
//    }
//
//    private static List<Comment> getComments() {
//        return IntStream.range(1, 4).boxed()
//                .map(id -> new Comment(id, "Comment_" + id, getDbBooks().get(id - 1)))
//                .toList();
//    }
//
//    private static List<Book> getDbBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
//        return IntStream.range(1, 4).boxed()
//                .map(id -> new Book(id,
//                        "BookTitle_" + id,
//                        dbAuthors.get(id - 1),
//                        dbGenres.subList((id - 1) * 2, (id - 1) * 2 + 2)
//                ))
//                .toList();
//    }
//
//    private static List<Book> getDbBooks() {
//        var dbAuthors = getDbAuthors();
//        var dbGenres = getDbGenres();
//        return getDbBooks(dbAuthors, dbGenres);
//    }
}
