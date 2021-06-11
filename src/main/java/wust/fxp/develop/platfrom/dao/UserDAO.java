package wust.fxp.develop.platfrom.dao;

import org.apache.ibatis.annotations.*;
import wust.fxp.develop.platfrom.entity.User;

import java.util.List;

/**
 * @author Evan
 * @date 2019/4
 */
@Mapper
public interface UserDAO {
    @Select("select * from user where username = #{username}")
    User findByUsername(String username);

    @Select("select * from user where username = #{username} and password = #{password}")
    User getByUsernameAndPassword(@Param("username") String username,@Param("password") String password);

    @Select("select * from user")
    List<User> findAll();

    @Select("insert into user(username,password,salt,name,phone,email,enabled) values(#{username},#{password},#{salt},#{name},#{phone},#{email},#{enabled})")
    User save(User userInDB);

    @Update("update user t set t.name = #{name},t.phone = #{phone},t.email = #{email} where id = #{id}")
    void updateUser(User user);

    @Delete("delete from user where id = #{id}")
    void deleteById(int id);
}
