package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.DocumentNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    public Optional<Comment> findById(String id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findByBookTitle(String title) {
        var book = bookRepository.findByTitle(title).orElseThrow(() -> new DocumentNotFoundException(
                String.format("Book with title %s not found", title)));
        return book.getComments();
    }

    @Override
    public Comment save(String text, String bookTitle) {
        Book book = bookRepository.findByTitle(bookTitle).orElseThrow(() -> new DocumentNotFoundException(
                String.format("Book with title %s not found", bookTitle)));
        Comment comment = new Comment(text);
        List<Comment> comments = book.getComments();
        comments.add(comment);
        commentRepository.save(comment);
        book.setComments(comments);
        bookRepository.save(book);
        return comment;
    }

    @Override
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }
}
