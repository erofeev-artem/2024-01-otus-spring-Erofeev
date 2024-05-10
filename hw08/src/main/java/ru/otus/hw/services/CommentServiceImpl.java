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
    public List<Comment> findByBookTitle(String bookTitle) {
        Book book = bookRepository.findByTitle(bookTitle).orElseThrow(() -> new DocumentNotFoundException(
                String.format("Book with title %s not found", bookTitle)));
        ;
        return commentRepository.findByBookId(book.getId());
    }

    @Override
    public Comment save(String text, String bookTitle) {
        Book book = bookRepository.findByTitle(bookTitle).orElseThrow(() -> new DocumentNotFoundException(
                String.format("Book with title %s not found", bookTitle)));
        return commentRepository.save(new Comment(text, book));
    }

    @Override
    public Comment update(String id, String text) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new DocumentNotFoundException(
                String.format("Comment with id %s not found", id)));
        comment.setText(text);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }
}
