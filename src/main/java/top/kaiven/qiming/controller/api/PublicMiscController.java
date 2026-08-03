package top.kaiven.qiming.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.kaiven.qiming.common.Result;
import top.kaiven.qiming.entity.Category;
import top.kaiven.qiming.entity.SiteConfig;
import top.kaiven.qiming.entity.VisitLog;
import top.kaiven.qiming.service.CategoryService;
import top.kaiven.qiming.mapper.VisitLogMapper;
import top.kaiven.qiming.service.SiteConfigService;

import java.util.List;

/**
 * 前台通用接口 — 分类、站点配置、访客记录（无需 JWT）
 */
@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicMiscController {

    private final CategoryService categoryService;
    private final SiteConfigService siteConfigService;
    private final VisitLogMapper visitLogMapper;

    @GetMapping("/categories")
    public Result<List<Category>> categories() {
        return Result.ok(categoryService.listAll());
    }

    @GetMapping("/site-config")
    public Result<List<SiteConfig>> siteConfig() {
        return Result.ok(siteConfigService.listAll());
    }

    @PostMapping("/visit-log")
    public Result<?> visitLog(@RequestBody VisitLog log) {
        visitLogMapper.insert(log);
        return Result.ok();
    }
}
