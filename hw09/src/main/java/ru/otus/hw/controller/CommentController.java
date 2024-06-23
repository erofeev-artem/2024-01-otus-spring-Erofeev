package ru.otus.hw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.services.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public String saveComment(long bookId, String commentText) {
        commentService.save(0, commentText, bookId);
        return "redirect:/book/info?id=" + bookId;
    }

    @GetMapping("/add")
    public String addComment(@RequestParam String bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "createComment";
    }
}
