package top.kaiven.qiming.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.kaiven.qiming.annotation.LogOperation;
import top.kaiven.qiming.common.Result;
import top.kaiven.qiming.dto.ArticleDTO;
import top.kaiven.qiming.entity.Article;
import top.kaiven.qiming.service.ArticleService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 后台图文笔记管理（需 JWT + admin role）
 * 操作日志由 @LogOperation + LogAspect 统一处理。
 */
@RestController
@RequestMapping("/api/admin/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public Result<IPage<Article>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {
        return Result.ok(articleService.pageAdmin(page, size, keyword));
    }

    @LogOperation(action = "新增笔记", target = "'title=' + #dto.title")
    @PostMapping
    public Result<Article> create(@Valid @RequestBody ArticleDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Article article = articleService.save(dto, userId);
        return Result.ok("新增成功", article);
    }

    @LogOperation(action = "编辑笔记", target = "'id=' + #id")
    @PutMapping("/{id}")
    public Result<Article> update(@PathVariable Long id, @Valid @RequestBody ArticleDTO dto, HttpServletRequest request) {
        dto.setId(id);
        Long userId = (Long) request.getAttribute("userId");
        Article article = articleService.update(dto, userId);
        return Result.ok("编辑成功", article);
    }

    @LogOperation(action = "删除笔记", target = "'id=' + #id")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        articleService.delete(id);
        return Result.ok("删除成功");
    }
}
