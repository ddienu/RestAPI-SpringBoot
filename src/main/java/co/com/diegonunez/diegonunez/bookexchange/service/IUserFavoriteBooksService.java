package co.com.diegonunez.diegonunez.bookexchange.service;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;

import java.util.List;

public interface IUserFavoriteBooksService {

    List<Book> getUserFavoriteBooks(Integer userId);
    void addFavoriteBooks (Integer userId, String bookISBN);
    void deleteFavoriteBooks (Integer userId, String bookISBN);
}
