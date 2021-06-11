package wust.fxp.develop.platfrom.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Category entity.
 *
 * @author Evan
 * @date 2019/4
 */
@Data
@ToString
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Category{

    private int id;

    /**
     * Category name in Chinese.
     */
    private String name;
}
