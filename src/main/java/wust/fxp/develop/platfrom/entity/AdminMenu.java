package wust.fxp.develop.platfrom.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Menu entity
 *
 * @author Evan
 * @date 2019/11
 */
@Data
@ToString
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class AdminMenu {

    private int id;

    /**
     * Menu access path.
     */
    private String path;

    /**
     * Menu name.
     */
    private String name;

    /**
     * Menu name in Chinese.
     */
    private String nameZh;

    /**
     * Menu icon class(use element-ui icons).
     */
    private String iconCls;

    /**
     * Front-end component name corresponding to menu.
     */
    private String component;

    /**
     * Parent menu.
     */
    private int parentId;

    /**
     * Transient property for storing children menus.
     */
    private List<AdminMenu> children;
}
