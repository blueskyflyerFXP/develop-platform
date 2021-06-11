package wust.fxp.develop.platfrom.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

/**
 * Role entity.
 *
 * @author Evan
 * @date 2019/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class AdminRole {
    private int id;

    /**
     * Role name.
     */
    private String name;

    /**
     * Role name in Chinese.
     */
    private String nameZh;

    /**
     * Role status.
     */
    private boolean enabled;


    /**
     * Transient property for storing permissions owned by current role.
     */
    private List<AdminPermission> perms;

    /**
     * Transient property for storing menus owned by current role.
     */
    private List<AdminMenu> menus;
}
