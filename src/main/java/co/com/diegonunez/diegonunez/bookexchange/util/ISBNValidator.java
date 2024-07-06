package co.com.diegonunez.diegonunez.bookexchange.util;

import co.com.diegonunez.diegonunez.bookexchange.exception.InvalidISBNException;

public class ISBNValidator {
    //Private constructor to avoid the class instantiation
    private ISBNValidator(){
        throw new UnsupportedOperationException("Utility class");
    }

    public static void validateIsbn(String isbn) throws InvalidISBNException {
        if (isbn.length() != 13 && isbn.length() != 10) {
            throw new InvalidISBNException("The ISBN must contain 10 or 13 numbers");
        }
    }
}
