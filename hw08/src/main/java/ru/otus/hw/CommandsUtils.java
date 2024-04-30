package ru.otus.hw;

import org.apache.commons.lang3.StringUtils;

public class CommandsUtils {

    public static String getIdFromBook(String book) {
        return StringUtils.substringBetween(book, "Id: ", ", title");
    }

    public static String getIdFromAuthor(String author) {
        return StringUtils.substringBetween(author, "Id: ", ",");
    }

    public static String getIdFromGenre(String genre) {
        return StringUtils.substringBetween(genre, "Id: ", ",");
    }
}
