package co.com.diegonunez.diegonunez.bookexchange.dto;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
