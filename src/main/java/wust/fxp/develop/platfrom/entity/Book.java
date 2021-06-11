package wust.fxp.develop.platfrom.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Book entity.
 *
 * @author Evan
 * @date 2019/4
 */
@Data
@ToString
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Book {

    private int id;

    /**
     * Title of the book.
     */
    private String title;

    /**
     * Author name.
     */
    private String author;

    /**
     * Publication date.
     */
    private String date;

    /**
     * Press.
     */
    private String press;

    /**
     * Abstract of the book.
     */
    private String abs;

    /**
     * The url of the book's cover.
     */
    private String cover;

    /**
     * Category id.
     */
    private String cid;
}