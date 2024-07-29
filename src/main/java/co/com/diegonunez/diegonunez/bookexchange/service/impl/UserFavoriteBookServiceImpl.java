package co.com.diegonunez.diegonunez.bookexchange.service.impl;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import co.com.diegonunez.diegonunez.bookexchange.entity.User;
import co.com.diegonunez.diegonunez.bookexchange.entity.UserFavoriteBooks;
import co.com.diegonunez.diegonunez.bookexchange.exception.NoBookFoundException;
import co.com.diegonunez.diegonunez.bookexchange.repository.IUserFavoriteBooksRepository;
import co.com.diegonunez.diegonunez.bookexchange.repository.IUserRepository;
import co.com.diegonunez.diegonunez.bookexchange.service.IUserFavoriteBooksService;
import co.com.diegonunez.diegonunez.bookexchange.util.ISBNValidator;
import jakarta.transaction.Transactional;
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

    //Method to get the user favorite books, throws a NoBookFoundException when the list is empty.
    @Override
    public List<Book> getUserFavoriteBooks(Integer userId) throws NoBookFoundException{
        List<UserFavoriteBooks> userFavoriteBooks = favoriteBooksRepository.findBooksByUserId(userId);
        if( userFavoriteBooks.isEmpty()){
            throw new NoBookFoundException("No favorite books founded for this user");
        }
        return userFavoriteBooks.stream()
                .map(ufb -> bookService.getBookByISBN(String.valueOf(ufb.getBookISBN())))
                .toList();
    }

    //Method to add new user favorite book, it permits the persistence into database, saving the userId and the book ISBN.
    @Override
    public void addFavoriteBooks(Integer userId, String bookISBN){
        ISBNValidator.validateIsbn(bookISBN);
        User userFounded = userRepository.getReferenceById(userId);
        UserFavoriteBooks favoriteBook = new UserFavoriteBooks();
        favoriteBook.setBookISBN(bookISBN);
        favoriteBook.setUser(userFounded);
        favoriteBooksRepository.save(favoriteBook);
    }

    //Method to erase a book from the user favorite book list it throws an exception if the list is empty.
    @Override
    @Transactional
    public void deleteFavoriteBook(Integer userId, String bookISBN) {
        ISBNValidator.validateIsbn(bookISBN);
        favoriteBooksRepository.deleteByUserIdAndBookISBN(userId, bookISBN);
    }
}
