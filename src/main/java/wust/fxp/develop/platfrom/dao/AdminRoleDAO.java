package wust.fxp.develop.platfrom.dao;

import org.apache.ibatis.annotations.*;
import wust.fxp.develop.platfrom.entity.AdminRole;

import java.util.List;

/**
 * @author Evan
 * @date 2019/11
 */
@Mapper
public interface AdminRoleDAO{
    @Select("select * from admin_role where id = #{id}")
    AdminRole findById(int id);
    @Select("select * from admin_role")
    List<AdminRole> findAll();
    @Insert("insert into admin_role(name,name_zh,enabled) values(#{name},#{nameZh},#{enabled})")
    void save(AdminRole adminRole);
    @Update("update admin_role set enabled=#{enabled} where id=#{id}")
    int updateStates(AdminRole adminRole);
}
