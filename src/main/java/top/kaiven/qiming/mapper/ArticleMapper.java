package top.kaiven.qiming.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import top.kaiven.qiming.entity.Article;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    /** 原子递增浏览量，避免并发 Read-Modify-Write 竞态 */
    @Update("UPDATE article SET view_count = view_count + 1 WHERE id = #{id}")
    void incrementView(@Param("id") Long id);

    /** 全量分页（不按分类筛选，含分类名+作者名） */
    @Select("SELECT a.id, a.title, LEFT(a.content, 300) AS content, a.cover_url, a.category_id, a.view_count, " +
            "a.user_id, a.created_at, a.updated_at, a.deleted, " +
            "c.name AS category_name, u.nickname AS author_name " +
            "FROM article a " +
            "LEFT JOIN category c ON a.category_id = c.id " +
            "LEFT JOIN user u ON a.user_id = u.id " +
            "WHERE a.deleted = 0 ORDER BY a.created_at DESC")
    IPage<Article> selectPageAll(Page<Article> page);

    /** 按分类分页（含分类名+作者名） */
    @Select("SELECT a.id, a.title, LEFT(a.content, 300) AS content, a.cover_url, a.category_id, a.view_count, " +
            "a.user_id, a.created_at, a.updated_at, a.deleted, " +
            "c.name AS category_name, u.nickname AS author_name " +
            "FROM article a " +
            "LEFT JOIN category c ON a.category_id = c.id " +
            "LEFT JOIN user u ON a.user_id = u.id " +
            "WHERE a.deleted = 0 AND a.category_id = #{categoryId} ORDER BY a.created_at DESC")
    IPage<Article> selectPageByCategory(Page<Article> page, @Param("categoryId") Long categoryId);

    /** 管理后台：关键词搜索分页（含分类名+作者名） */
    @Select("SELECT a.id, a.title, LEFT(a.content, 300) AS content, a.cover_url, a.category_id, a.view_count, " +
            "a.user_id, a.created_at, a.updated_at, a.deleted, " +
            "c.name AS category_name, u.nickname AS author_name " +
            "FROM article a " +
            "LEFT JOIN category c ON a.category_id = c.id " +
            "LEFT JOIN user u ON a.user_id = u.id " +
            "WHERE a.deleted = 0 " +
            "AND (#{keyword} IS NULL OR #{keyword} = '' OR a.title LIKE CONCAT('%',#{keyword},'%')) " +
            "ORDER BY a.created_at DESC")
    IPage<Article> selectPageAdmin(Page<Article> page, @Param("keyword") String keyword);
}
