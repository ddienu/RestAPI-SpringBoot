package co.com.diegonunez.diegonunez.bookexchange.dto;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Data implements Serializable {
    @JsonProperty("message")
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("book")
    private Book book;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("bookList")
    private List<Book> books;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("token")
    private String token;

    public Data(String message, List<Book> books){
        this.message = message;
        this.books = books;
    }

    public Data(String message, Book book){
        this.message = message;
        this.book = book;
    }

    public Data(String message, String token){
        this.message = message;
        this.token = token;
    }
    //Constructor for books property
    public Data(List<Book> books){
        this.books = books;
    }

    //Constructor for book property
    public Data(Book book){
        this.book = book;
    }

    public Data(String message){
        this.message = message;
    }

}
