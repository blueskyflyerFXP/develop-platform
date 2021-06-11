package wust.fxp.develop.platfrom.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import wust.fxp.develop.platfrom.entity.AdminUserRole;

import java.util.List;

/**
 * @author Evan
 * @date 2019/11
 */
@Mapper
public interface AdminUserRoleDAO {
    @Select("select * from admin_user_role where uid = #{uid}")
    List<AdminUserRole> findAllByUid(int uid);

    @Delete("delete from admin_user_role where uid = #{uid}")
    void deleteAllByUid(int uid);

    @Insert("insert into admin_user_role(id,uid,rid) values(#{id},#{uid},#{rid})")
    void save(AdminUserRole ur);
}
