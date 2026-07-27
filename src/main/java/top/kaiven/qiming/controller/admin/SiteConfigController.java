package top.kaiven.qiming.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.kaiven.qiming.common.Result;
import top.kaiven.qiming.entity.SiteConfig;
import top.kaiven.qiming.service.SiteConfigService;

import java.util.List;

/**
 * 后台站点设置（需 JWT + admin role）
 */
@RestController
@RequestMapping("/api/admin/site-config")
@RequiredArgsConstructor
public class SiteConfigController {

    private final SiteConfigService siteConfigService;

    @GetMapping
    public Result<List<SiteConfig>> list() {
        return Result.ok(siteConfigService.listAll());
    }

    @PutMapping
    public Result<?> update(@RequestBody List<SiteConfig> configs) {
        siteConfigService.updateBatch(configs);
        return Result.ok("保存成功");
    }
}
