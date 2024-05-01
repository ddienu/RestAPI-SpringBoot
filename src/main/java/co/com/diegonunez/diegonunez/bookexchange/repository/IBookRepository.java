package co.com.diegonunez.diegonunez.bookexchange.repository;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookRepository extends JpaRepository<Book, Integer> {

    Book findBookByBookName(String bookName);
    List<Book> findBooksByBookAuthor(String bookAuthor);
    Book getBookByBookISBN(String bookISBN);
    List<Book> getBooksByBookGenre(String bookGenre);
    @Transactional
    void deleteBookByBookISBN(String bookISBN);
}
