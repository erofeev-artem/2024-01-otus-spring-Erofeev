package ru.otus.hw.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;


@ChangeLog
public class DatabaseChangelog {
    private Book book;

    private Comment comment;

//    @ChangeSet(order = "001", id = "dropDb", author = "stvort", runAlways = true)
//    public void dropDb(MongoDatabase db) {
//        db.drop();
//    }

    @ChangeSet(order = "001", id = "insertAuthor_1", author = "aerofeev")
    public void insertAuthor1(AuthorRepository repository) {
        repository.save(new Author("Author_1"));
    }

    @ChangeSet(order = "002", id = "insertAuthor_2", author = "aerofeev")
    public void insertAuthor2(AuthorRepository repository) {
        repository.save(new Author("Author_2"));
    }

    @ChangeSet(order = "003", id = "insertAuthor_3", author = "aerofeev")
    public void insertAuthor3(AuthorRepository repository) {
        repository.save(new Author("Author_3"));
    }

    @ChangeSet(order = "004", id = "insertGenre_1", author = "aerofeev")
    public void insertGenre1(GenreRepository repository) {
        repository.save(new Genre("Genre_1"));
    }

    @ChangeSet(order = "005", id = "insertGenre_2", author = "aerofeev")
    public void insertGenre2(GenreRepository repository) {
        repository.save(new Genre("Genre_2"));
    }

    @ChangeSet(order = "006", id = "insertGenre_3", author = "aerofeev")
    public void insertGenre3(GenreRepository repository) {
        repository.save(new Genre("Genre_3"));
    }

    @ChangeSet(order = "007", id = "insertBook_1", author = "aerofeev")
    public void insertBook1(BookRepository repository) {

        repository.save(new Book("Pushkin", a));
    }



    @ChangeSet(order = "00", id = "insertComment_1", author = "aerofeev")
    public void insertComment1(CommentRepository repository) {
        repository.save(new Comment("Pushkin"));
    }
}
