package co.com.diegonunez.diegonunez.bookexchange.repository;

import co.com.diegonunez.diegonunez.bookexchange.entity.UserFavoriteBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IUserFavoriteBooksRepository extends JpaRepository<UserFavoriteBooks, Integer> {

    List<UserFavoriteBooks> findBooksByUserId(Integer userId);
    void deleteByUserIdAndBookISBN(Integer userId, List<String> bookISBN);
}
