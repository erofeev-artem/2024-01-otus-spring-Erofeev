package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.models.Book;
import ru.otus.hw.services.BookService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class BookCommands {
    private final BookService bookService;

    private final BookConverter bookConverter;

    @ShellMethod(value = "Find all books", key = "ab")
    public String findAllBooks() {
        List<Book> books = bookService.findAll();
        return books.stream().map(bookConverter::bookToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find book by id", key = "bbid")
    public String findBookById(String id) {
        return bookService.findById(id)
                .map(bookConverter::bookToString)
                .orElse("Book with id %s not found".formatted(id));
    }

    @ShellMethod(value = "Find book by title", key = "bbt")
    public String findBookByTitle(String title) {
        return bookService.findByTitle(title)
                .map(bookConverter::bookToString)
                .orElse("Book with title %s not found".formatted(title));
    }

    @ShellMethod(value = "Save book", key = "bins")
    public String saveBook(String title, String authorFullName, Set<String> genresNames) {
        var savedBook = bookService.saveOrUpdate(title, authorFullName, genresNames);
        return bookConverter.bookToString(savedBook);
    }

    @ShellMethod(value = "Delete book by title", key = "bdel")
    public void deleteBook(String title) {
        bookService.deleteByTitle(title);
    }
}
