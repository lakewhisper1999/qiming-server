package top.kaiven.qiming.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.kaiven.qiming.dto.ArticleDTO;
import top.kaiven.qiming.entity.Article;

public interface ArticleService {
    IPage<Article> pagePublic(int page, int size, Long categoryId);
    Article getById(Long id);
    void incrementView(Long id);
    IPage<Article> pageAdmin(int page, int size, String keyword);
    Article save(ArticleDTO dto, Long userId);
    Article update(ArticleDTO dto, Long userId);
    void delete(Long id);
}
