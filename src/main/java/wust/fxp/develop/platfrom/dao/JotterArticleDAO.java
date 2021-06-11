package wust.fxp.develop.platfrom.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import org.springframework.data.domain.PageRequest;
import wust.fxp.develop.platfrom.entity.JotterArticle;

/**
 * @author Evan
 * @date 2020/1/14 20:40
 */
@Mapper
public interface JotterArticleDAO {

    @Select("select * from jotter_article where id = #{id}")
    JotterArticle findById(int id);

    @Select("select * from jotter_article")
    Page<JotterArticle> findAll(Page page);

    @Insert("insert into jotter_article(article_title,article_content_html,article_content_md,article_abstract,article_cover,article_date) values(#{articleTitle},#{articleContentHtml},#{articleContentMd},#{articleAbstract},#{articleCover},#{articleDate})")
    void save(JotterArticle article);

    @Delete("delete from jotter_article where id = #{id}")
    void deleteById(int id);
}
