package wust.fxp.develop.platfrom.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;


/**
 * Relations between users and role.
 *
 * @author Evan
 * @date 2019/11
 */
@Data
@ToString
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class AdminUserRole {

    private int id;

    /**
     * User id.
     */
    private int uid;

    /**
     * Role id.
     */
    private int rid;
}
