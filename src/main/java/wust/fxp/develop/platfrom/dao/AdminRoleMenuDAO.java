package wust.fxp.develop.platfrom.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import wust.fxp.develop.platfrom.entity.AdminRoleMenu;

import java.util.List;

/**
 * @author Evan
 * @date 2019/11
 */
@Mapper
public interface AdminRoleMenuDAO{

    @Select("select * from admin_role_menu where rid =#{rid}")
    List<AdminRoleMenu> findAllByRid(Integer rid);

    @Delete("delete from admin_role_menu where rid=#{rid}")
    void deleteAllByRid(int rid);

    @Insert("insert into admin_role_menu(id,rid,mid) values(#{id},#{rid},#{mid})")
    void save(AdminRoleMenu rm);
}
