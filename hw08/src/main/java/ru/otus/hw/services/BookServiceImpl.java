package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.DocumentNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    @Override
    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book saveOrUpdate(String title, String authorFullName, Set<String> genresNames) {
        Book book = bookRepository.findByTitle(title).orElse(new Book());
        book.setTitle(title);
        Author author = authorRepository.findByFullName(authorFullName).orElseThrow(() ->
                new DocumentNotFoundException(String.format("Author with full name %s not found", authorFullName)));
        book.setAuthor(author);
        Set<Genre> newGenres = new HashSet<>();
        for (String genreName : genresNames) {
            newGenres.add(genreRepository.findByName(genreName).orElseThrow(() ->
                    new DocumentNotFoundException(String.format("Genre with name %s not found", genreName))));
        }
        book.setGenres(newGenres);
        return bookRepository.save(book);
    }

    @Override
    public void deleteByTitle(String title) {
        bookRepository.deleteBookByTitle(title);
    }
}
