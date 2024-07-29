package co.com.diegonunez.diegonunez.bookexchange.repository;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import co.com.diegonunez.diegonunez.bookexchange.entity.UserFavoriteBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IUserFavoriteBooksRepository extends JpaRepository<UserFavoriteBooks, Integer> {

    List<UserFavoriteBooks> findBooksByUserId(Integer userId);
    /*Book addBookISBNbyUserId(Integer userId, Book bookISBN);*/
    void deleteByUserIdAndBookISBN(Integer userId, Book bookISBN);
}
