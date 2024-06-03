package co.com.diegonunez.diegonunez.bookexchange.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.io.Serializable;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book implements Serializable {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;
    @Column(name = "book_isbn")
    @NotBlank(message = "The ISBN cannot be empty")
    private String bookISBN;
    @Column(name = "book_name")
    @NotBlank(message = "The field book name cannot be empty")
    private String bookName;
    @Column(name = "book_author")
    @NotBlank(message = "The field book author cannot be empty")
    private String bookAuthor;
    @Column(name = "is_available")
    private Boolean isAvailable;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "book_release")
    private String bookRelease;
    @Column(name = "book_genre")
    private String bookGenre;
    @Column(name = "book_description")
    private String bookDescription;
    @Column(name = "book_condition")
    private String bookCondition;
    @Column(name = "book_image")
    private String bookImage;
    @Column(name = "book_publisher")
    private String bookPublisher;
    @Column(name = "book_language")
    private String bookLanguage;


}
