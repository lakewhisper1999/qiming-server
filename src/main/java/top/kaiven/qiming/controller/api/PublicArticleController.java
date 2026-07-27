package top.kaiven.qiming.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.kaiven.qiming.common.Result;
import top.kaiven.qiming.entity.Article;
import top.kaiven.qiming.service.ArticleService;

/**
 * 前台图文笔记接口（无需 JWT）
 */
@RestController
@RequestMapping("/api/public/articles")
@RequiredArgsConstructor
public class PublicArticleController {

    private final ArticleService articleService;

    @GetMapping
    public Result<IPage<Article>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long categoryId) {
        return Result.ok(articleService.pagePublic(page, size, categoryId));
    }

    @GetMapping("/{id}")
    public Result<Article> detail(@PathVariable Long id) {
        articleService.incrementView(id);
        return Result.ok(articleService.getById(id));
    }
}
