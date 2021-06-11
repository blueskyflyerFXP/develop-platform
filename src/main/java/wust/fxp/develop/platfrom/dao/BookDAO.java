package wust.fxp.develop.platfrom.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Sort;
import wust.fxp.develop.platfrom.entity.Book;
import wust.fxp.develop.platfrom.entity.Category;

import java.util.List;

/**
 * @author Evan
 * @date 2019/4
 */
@Mapper
public interface BookDAO {
    @Select("select * from book where cid= #{categoryId}")
    List<Book> findAllByCategory(int categoryId);
    @Select("select * from book where title like #{keyword1} or author like #{keyword2} ")
    List<Book> findAllByTitleLikeOrAuthorLike(@Param("keyword1") String keyword1,@Param("keyword2") String keyword2);
    @Select("delete from book where id= #{id}")
    void deleteById(int id);
    @Insert("insert into book(title,author,date,press,abs,cover,cid) values(#{title},#{author},#{date},#{press},#{abs},#{cover},#{cid})")
    void save(Book book);
    @Select("select * from book")
    List<Book> findAll(Sort sort);
}
