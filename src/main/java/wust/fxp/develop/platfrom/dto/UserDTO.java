package wust.fxp.develop.platfrom.dto;

import lombok.Data;
import lombok.ToString;
import wust.fxp.develop.platfrom.dto.base.OutputConverter;
import wust.fxp.develop.platfrom.entity.AdminRole;
import wust.fxp.develop.platfrom.entity.User;

import java.util.List;

/**
 * @author Evan
 * @date 2020/4/1 19:57
 */
@Data
@ToString
public class UserDTO implements OutputConverter<UserDTO, User> {

    private int id;

    private String username;

    private String name;

    private String phone;

    private String email;

    private boolean enabled;

    private List<AdminRole> roles;

}
