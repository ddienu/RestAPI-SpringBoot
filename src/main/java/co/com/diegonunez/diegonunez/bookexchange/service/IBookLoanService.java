package co.com.diegonunez.diegonunez.bookexchange.service;

public interface IBookLoanService {

    void requestBook(Integer userId, String bookISBN);
    void returnBook(Integer userId, String bookISBN);
}
