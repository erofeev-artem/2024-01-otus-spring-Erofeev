package ru.otus.hw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.GenreService;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final CommentService commentService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService, GenreService genreService,
                          CommentService commentService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.commentService = commentService;
    }

    @GetMapping("/all")
    public String allBooks(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/edit")
    public String editBook(@RequestParam("id") long id, Model model) {
        Book book = bookService.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Book with id " + id + " not found"));
        model.addAttribute("book", book);
        List<Author> authorList = authorService.findAll();
        List<Genre> genreList = genreService.findAll();
        model.addAttribute("authorList", authorList);
        model.addAttribute("genreList", genreList);
        return "edit";
    }

    @GetMapping("/create")
    public String createBook(Model model) {
        model.addAttribute("title", "");
        List<Author> authorList = authorService.findAll();
        List<Genre> genreList = genreService.findAll();
        model.addAttribute("authorList", authorList);
        model.addAttribute("genreList", genreList);
        return "create";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("id") long id) {
        bookService.deleteById(id);
        return "redirect:/book/all";
    }

    @PostMapping("/create")
    public String saveBook(String title, String authorId, String genresIds) {
        String[] strings = genresIds.split(",");
        Set<Long> longGenresIds = Arrays.stream(strings).map(Long::parseLong).collect(Collectors.toSet());
        bookService.insert(title, Long.parseLong(authorId), longGenresIds);
        return "redirect:/book/all";
    }

    @PostMapping("/edit")
    public String updateBook(long id, String title, String authorId, String genresIds) {
        String[] strings = genresIds.split(",");
        Set<Long> longGenresIds = Arrays.stream(strings).map(Long::parseLong).collect(Collectors.toSet());
        bookService.update(id, title, Long.parseLong(authorId), longGenresIds);
        return "redirect:/book/all";
    }

    @GetMapping("/info")
    public String bookInfo(@RequestParam("id") long id, Model model) {
        Book book = bookService.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Book with id " + id + " not found"));
        model.addAttribute("book", book);
        List<Comment> comments = commentService.findByBookId(book.getId());
        List<Author> authorList = authorService.findAll();
        List<Genre> genreList = genreService.findAll();
        model.addAttribute("authorList", authorList);
        model.addAttribute("genreList", genreList);
        model.addAttribute("comments", comments);
        return "info";
    }
}
