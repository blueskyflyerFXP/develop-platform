package wust.fxp.develop.platfrom.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Sort;
import wust.fxp.develop.platfrom.entity.Category;

import java.util.List;
import java.util.Optional;

/**
 * @author Evan
 * @date 2019/4
 */
@Mapper
public interface CategoryDAO{


    @Select("select * from category where id = #{id} or id is null")
    Category findById(int id);

    @Select("select * from category")
    List<Category> findAll(Sort sort);
}
