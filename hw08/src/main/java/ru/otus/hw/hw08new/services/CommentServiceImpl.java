package ru.otus.hw.hw08new.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.hw08new.models.Book;
import ru.otus.hw.hw08new.models.Comment;
import ru.otus.hw.hw08new.repositories.BookRepository;
import ru.otus.hw.hw08new.repositories.CommentRepository;

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
    public List<Comment> findByBookId(String id) {
        return commentRepository.findByBookId(id);
    }

    @Override
    public Comment save(String id, String text, String bookId) {
        Book book = bookRepository.findById(bookId).get();
        Comment comment = new Comment(id, text, book);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }
}
