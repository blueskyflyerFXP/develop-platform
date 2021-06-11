package wust.fxp.develop.platfrom.dao;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import wust.fxp.develop.platfrom.entity.AdminRolePermission;

import java.util.List;

/**
 * @author Evan
 * @date 2019/11
 */
@Mapper
public interface AdminRolePermissionDAO {

    @Select("select * from admin_role_permission where rid =${rid}")
    List<AdminRolePermission> findAllByRid(Integer rid);

    @Delete("delete from admin_role_permission where rid=#{rid}")
    void deleteAllByRid(int rid);

    @Insert("insert into admin_role_permission(id,rid,pid) values(#{id},#{rid},#{pid})")
    void save(AdminRolePermission rp);
}
