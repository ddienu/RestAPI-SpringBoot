package co.com.diegonunez.diegonunez.bookexchange.service.impl;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import co.com.diegonunez.diegonunez.bookexchange.entity.User;
import co.com.diegonunez.diegonunez.bookexchange.entity.UserFavoriteBooks;
import co.com.diegonunez.diegonunez.bookexchange.exception.NoBookFoundException;
import co.com.diegonunez.diegonunez.bookexchange.repository.IUserFavoriteBooksRepository;
import co.com.diegonunez.diegonunez.bookexchange.repository.IUserRepository;
import co.com.diegonunez.diegonunez.bookexchange.service.IUserFavoriteBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserFavoriteBookServiceImpl implements IUserFavoriteBooksService {

    private final IUserFavoriteBooksRepository favoriteBooksRepository;
    private final IUserRepository userRepository;
    private final BookServiceImpl bookService;

    @Autowired
    public UserFavoriteBookServiceImpl(IUserFavoriteBooksRepository favoriteBooksRepository, BookServiceImpl bookService, IUserRepository userRepository){
        this.favoriteBooksRepository = favoriteBooksRepository;
        this.bookService = bookService;
        this.userRepository = userRepository;
    }
    @Override
    public List<Book> getUserFavoriteBooks(Integer userId) {
        List<UserFavoriteBooks> userFavoriteBooks = favoriteBooksRepository.findBooksByUserId(userId);
        if( userFavoriteBooks.isEmpty()){
            throw new NoBookFoundException("No favorite books founded for this user");
        }
        return userFavoriteBooks.stream()
                .map(ufb -> bookService.getBookByISBN(String.valueOf(ufb.getBookISBN())))
                .toList();
    }

    @Override
    public void addFavoriteBooks(Integer userId, String bookISBN){
        User userFounded = userRepository.getReferenceById(userId);
        UserFavoriteBooks newFavoriteBook1 = new UserFavoriteBooks();
        newFavoriteBook1.setBookISBN(bookISBN);
        newFavoriteBook1.setUser(userFounded);
        favoriteBooksRepository.save(newFavoriteBook1);
    }

    @Override
    public void deleteFavoriteBooks(Integer userId, String bookISBN) {
    }
}
