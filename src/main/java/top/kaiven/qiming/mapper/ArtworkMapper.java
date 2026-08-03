package top.kaiven.qiming.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import top.kaiven.qiming.entity.Artwork;

@Mapper
public interface ArtworkMapper extends BaseMapper<Artwork> {

    /** 原子递增浏览量，避免并发 Read-Modify-Write 竞态 */
    @Update("UPDATE artwork SET view_count = view_count + 1 WHERE id = #{id}")
    void incrementView(@Param("id") Long id);

    /** 前台分页（含分类名，categoryId 为空则不过滤） */
    @Select("SELECT a.id, a.title, a.description, a.cover_url, a.image_urls, a.category_id, a.download_url, a.video_url, " +
            "a.file_size, a.view_count, a.download_count, a.user_id, a.created_at, a.updated_at, a.deleted, " +
            "c.name AS category_name " +
            "FROM artwork a LEFT JOIN category c ON a.category_id = c.id " +
            "WHERE a.deleted = 0 " +
            "AND (#{categoryId} IS NULL OR a.category_id = #{categoryId}) " +
            "ORDER BY a.created_at DESC")
    IPage<Artwork> selectPagePublic(Page<Artwork> page, @Param("categoryId") Long categoryId);

    /** 管理后台：关键词+分类联合搜索分页 */
    @Select("SELECT a.id, a.title, a.description, a.cover_url, a.image_urls, a.category_id, a.download_url, a.video_url, " +
            "a.file_size, a.view_count, a.download_count, a.user_id, a.created_at, a.updated_at, a.deleted, " +
            "c.name AS category_name " +
            "FROM artwork a LEFT JOIN category c ON a.category_id = c.id " +
            "WHERE a.deleted = 0 " +
            "AND (#{keyword} IS NULL OR #{keyword} = '' OR a.title LIKE CONCAT('%',#{keyword},'%')) " +
            "AND (#{categoryId} IS NULL OR a.category_id = #{categoryId}) " +
            "ORDER BY a.created_at DESC")
    IPage<Artwork> selectPageAdmin(Page<Artwork> page,
                                   @Param("keyword") String keyword,
                                   @Param("categoryId") Long categoryId);
}
