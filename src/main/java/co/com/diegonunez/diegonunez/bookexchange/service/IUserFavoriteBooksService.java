package co.com.diegonunez.diegonunez.bookexchange.service;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import co.com.diegonunez.diegonunez.bookexchange.exception.NoBookFoundException;

import java.util.List;

public interface IUserFavoriteBooksService {

    List<Book> getUserFavoriteBooks(Integer userId) throws NoBookFoundException;
    void addFavoriteBooks (Integer userId, String bookISBN);
    void deleteFavoriteBook (Integer userId, String bookISBN);
}
