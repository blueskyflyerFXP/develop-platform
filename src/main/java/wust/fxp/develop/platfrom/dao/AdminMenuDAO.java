package wust.fxp.develop.platfrom.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import wust.fxp.develop.platfrom.entity.AdminMenu;

import java.util.List;

/**
 * @author Evan
 * @date 2020/1/10
 */
@Mapper
public interface AdminMenuDAO {
     @Select("select * from admin_menu where id =#{id}")
     AdminMenu findById(int id);

     @Select("select * from admin_menu where parent_id =#{parentId}")
     List<AdminMenu> findAllByParentId(int parentId);

}

