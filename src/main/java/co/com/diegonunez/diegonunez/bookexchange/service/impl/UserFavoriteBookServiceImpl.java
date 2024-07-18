package co.com.diegonunez.diegonunez.bookexchange.service.impl;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import co.com.diegonunez.diegonunez.bookexchange.entity.UserFavoriteBooks;
import co.com.diegonunez.diegonunez.bookexchange.repository.IUserFavoriteBooksRepository;
import co.com.diegonunez.diegonunez.bookexchange.service.IUserFavoriteBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserFavoriteBookServiceImpl implements IUserFavoriteBooksService {

    private final IUserFavoriteBooksRepository favoriteBooksRepository;
    private final BookServiceImpl bookService;

    @Autowired
    public UserFavoriteBookServiceImpl(IUserFavoriteBooksRepository favoriteBooksRepository, BookServiceImpl bookService){
        this.favoriteBooksRepository = favoriteBooksRepository;
        this.bookService = bookService;
    }
    @Override
    public List<Book> getUserFavoriteBooks(Integer userId) {
        List<UserFavoriteBooks> userFavoriteBooks = favoriteBooksRepository.findBooksByUserId(userId);
        return userFavoriteBooks.stream()
                .map(ufb -> bookService.getBookByISBN(String.valueOf(ufb.getBookISBN())))
                .collect(Collectors.toList());
    }

    @Override
    public void addFavoriteBooks(Integer userId, String bookISBN){
    }

    @Override
    public void deleteFavoriteBooks(Integer userId, String bookISBN) {
    }
}
