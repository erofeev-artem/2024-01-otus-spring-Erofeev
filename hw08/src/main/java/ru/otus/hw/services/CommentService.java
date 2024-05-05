package ru.otus.hw.services;


import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CommentService {
    Optional<Comment> findById(String id);

    List<Comment> findByBookTitle(String title);

    Comment save(String text, String bookTitle);

    void deleteById(String id);
}
