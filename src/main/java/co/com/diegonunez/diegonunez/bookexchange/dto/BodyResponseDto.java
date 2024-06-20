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
@AllArgsConstructor
@NoArgsConstructor
public class BodyResponseDto implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Book")
    private Book book;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("BookList")
    private List<Book> books;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("token")
    private String token;

    //Constructor for books property
    public BodyResponseDto(List<Book> books){
        this.books = books;
    }

    //Constructor for book property
    public BodyResponseDto(Book book){
        this.book = book;
    }

    public BodyResponseDto(String token) {
        this.token = token;
    }
}
