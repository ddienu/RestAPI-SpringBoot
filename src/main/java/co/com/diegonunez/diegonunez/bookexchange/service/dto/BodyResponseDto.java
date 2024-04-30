package co.com.diegonunez.diegonunez.bookexchange.service.dto;

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
}
