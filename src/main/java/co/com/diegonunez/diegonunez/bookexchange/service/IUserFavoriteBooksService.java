package co.com.diegonunez.diegonunez.bookexchange.service;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;

import java.util.List;

public interface IUserFavoriteBooksService {

    List<Book> getUserFavoriteBooks(Integer userId);
    void addFavoriteBooks (Integer userId, List<String> bookISBNs);
    void deleteFavoriteBooks (Integer userId, List<String> bookISBNs);
}
