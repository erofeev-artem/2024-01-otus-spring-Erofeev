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
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;


@ChangeLog(order = "001")
public class DatabaseChangelog {
    private Book book;

    private Comment comment;

    @ChangeSet(order = "001", id = "dropDb", author = "aerofeev", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "aerofeev")
    public void insertAuthors(AuthorRepository repository) {
        repository.save(new Author("Author_1"));
        repository.save(new Author("Author_2"));
        repository.save(new Author("Author_3"));
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "aerofeev")
    public void insertGenres(GenreRepository repository) {
        repository.save(new Genre("Genre_1"));
        repository.save(new Genre("Genre_2"));
        repository.save(new Genre("Genre_3"));
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "aerofeev")
    public void insertBook1(BookRepository bookRepository, AuthorRepository authorRepository,
                            GenreRepository genreRepository) {
        List<Genre> allGenres = genreRepository.findAll();
        List<Author> allAuthors = authorRepository.findAll();
        bookRepository.save(new Book("Fahrenheit 451", allAuthors.get(0), List.of(allGenres.get(0),
                allGenres.get(2))));
        bookRepository.save(new Book("Atlas shrugged", allAuthors.get(1), List.of(allGenres.get(2))));
        bookRepository.save(new Book("Starship troopers", allAuthors.get(2), List.of(allGenres.get(0),
                allGenres.get(2))));
    }


//    @ChangeSet(order = "00", id = "insertComment_1", author = "aerofeev")
//    public void insertComment1(CommentRepository repository) {
//        repository.save(new Comment("Pushkin"));
//    }
}
