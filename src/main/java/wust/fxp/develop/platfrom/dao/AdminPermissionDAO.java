package wust.fxp.develop.platfrom.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import wust.fxp.develop.platfrom.entity.AdminPermission;

import java.util.List;

/**
 * @author Evan
 * @date 2019/11
 */
@Mapper
public interface AdminPermissionDAO {
    @Select("select * from admin_permission where id = #{id}")
    AdminPermission findById(int id);

    @Select("select * from admin_permission")
    List<AdminPermission> findAll();
}
