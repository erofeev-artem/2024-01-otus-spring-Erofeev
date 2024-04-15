package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.services.BookService;

import java.util.Set;

@RequiredArgsConstructor
@ShellComponent
public class BookCommands {
    private final BookService bookService;

//    private final BookConverter bookConverter;

    @ShellMethod(value = "Find all books", key = "ab")
    public String findAllBooks() {
        System.out.println("");
        bookService.findAll();
//                .map(bookConverter::bookToString)
//                .collect(Collectors.joining("," + System.lineSeparator()));
        return null;
    }

    @ShellMethod(value = "Find book by id", key = "bbid")
    public String findBookById(String id) {
//        return bookService.findById(id)
//                .map(bookConverter::bookToString)
//                .orElse("Book with id %d not found".formatted(id));
        return null;
    }

    // bins newBook 1 1,6
    @ShellMethod(value = "Insert book", key = "bins")
    public String insertBook(String title, String authorId, Set<String> genresId) {
//        var savedBook = bookService.insert(title, authorId, genresId);
//        return bookConverter.bookToString(savedBook);
        return null;
    }

    // bupd 4 editedBook 3 2,5
    @ShellMethod(value = "Update book", key = "bupd")
    public String updateBook(String id, String title, String authorId, Set<String> genresId) {
//        var savedBook = bookService.update(id, title, authorId, genresId);
//        return bookConverter.bookToString(savedBook);
        return null;
    }

    // bdel 4
    @ShellMethod(value = "Delete book by id", key = "bdel")
    public void deleteBook(String id) {
        bookService.deleteById(id);
    }
}
