package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.services.BookService;

@Controller
@RequiredArgsConstructor
public class BookPageController {
    private final BookService bookService;

    @GetMapping("/")
    public String allBooks() {
        return "list";
    }

    @GetMapping("/edit")
    @PreAuthorize(value = "hasRole('admin')")
    public String editBook() {
        return "edit";
    }

    @GetMapping("/create")
    @PreAuthorize(value = "hasRole('admin')")
    public String createBook() {
        return "edit";
    }

    @GetMapping("/info")
    @PreAuthorize(value = "hasAnyRole('admin', 'user')")
    public String bookInfo(@RequestParam("id") long id) {
        return "info";
    }
}
