package ru.otus.hw.utils;

import org.apache.commons.lang3.StringUtils;

public class CommandsUtils {

    public static String getIdFromBook(String book) {
        return StringUtils.substringBetween(book, "Id: ", ", title");
    }
}
