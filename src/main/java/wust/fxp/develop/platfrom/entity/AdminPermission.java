package wust.fxp.develop.platfrom.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

/**
 * Permission entity.
 *
 * @author Evan
 * @date 2019/11
 */
@Data
@ToString
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class AdminPermission {
    private int id;

    /**
     * Permission name;
     */
    private String name;

    /**
     * Permission's description(in Chinese)
     */
    private String desc_;

    /**
     * The path which triggers permission check.
     */
    private String url;
}
