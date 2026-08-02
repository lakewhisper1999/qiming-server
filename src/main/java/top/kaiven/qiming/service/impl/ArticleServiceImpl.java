package top.kaiven.qiming.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.kaiven.qiming.common.BizException;
import top.kaiven.qiming.dto.ArticleDTO;
import top.kaiven.qiming.entity.Article;
import top.kaiven.qiming.mapper.ArticleMapper;
import top.kaiven.qiming.service.ArticleService;
import top.kaiven.qiming.common.PublishService;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;
    private final PublishService publishService;

    @Override
    public IPage<Article> pagePublic(int page, int size, Long categoryId) {
        Page<Article> pageObj = new Page<>(page, size);
        if (categoryId != null) {
            return articleMapper.selectPageByCategory(pageObj, categoryId);
        }
        return articleMapper.selectPageAll(pageObj);
    }

    @Override
    public Article getById(Long id) {
        return articleMapper.selectById(id);
    }

    @Override
    public void incrementView(Long id) {
        articleMapper.incrementView(id);
    }

    @Override
    public IPage<Article> pageAdmin(int page, int size, String keyword) {
        return articleMapper.selectPageAdmin(new Page<>(page, size),
                StringUtils.hasText(keyword) ? keyword : null);
    }

    @Override
    public Article save(ArticleDTO dto, Long userId) {
        Article article = new Article();
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setCoverUrl(dto.getCoverUrl());
        article.setCategoryId(dto.getCategoryId());
        article.setUserId(userId);
        article.setViewCount(0);
        articleMapper.insert(article);
        publishService.publishAsync();
        return article;
    }

    @Override
    public Article update(ArticleDTO dto, Long userId) {
        Article article = articleMapper.selectById(dto.getId());
        if (article == null) throw BizException.notFound("文章不存在");
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setCoverUrl(dto.getCoverUrl());
        article.setCategoryId(dto.getCategoryId());
        articleMapper.updateById(article);
        publishService.publishAsync();
        return article;
    }

    @Override
    public void delete(Long id) {
        articleMapper.deleteById(id);
        publishService.publishAsync();
    }
}
