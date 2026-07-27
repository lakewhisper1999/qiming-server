package top.kaiven.qiming.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.kaiven.qiming.annotation.LogOperation;
import top.kaiven.qiming.common.Result;
import top.kaiven.qiming.dto.ArtworkDTO;
import top.kaiven.qiming.entity.Artwork;
import top.kaiven.qiming.service.ArtworkService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 后台作品管理（需 JWT + admin role）
 * 操作日志由 @LogOperation + LogAspect 统一处理。
 */
@RestController
@RequestMapping("/api/admin/artworks")
@RequiredArgsConstructor
public class ArtworkController {

    private final ArtworkService artworkService;

    @GetMapping
    public Result<IPage<Artwork>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId) {
        return Result.ok(artworkService.pageAdmin(page, size, keyword, categoryId));
    }

    @LogOperation(action = "新增作品", target = "'title=' + #dto.title")
    @PostMapping
    public Result<Artwork> create(@Valid @RequestBody ArtworkDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Artwork artwork = artworkService.save(dto, userId);
        return Result.ok("新增成功", artwork);
    }

    @LogOperation(action = "编辑作品", target = "'id=' + #id")
    @PutMapping("/{id}")
    public Result<Artwork> update(@PathVariable Long id, @Valid @RequestBody ArtworkDTO dto, HttpServletRequest request) {
        dto.setId(id);
        Long userId = (Long) request.getAttribute("userId");
        Artwork artwork = artworkService.update(dto, userId);
        return Result.ok("编辑成功", artwork);
    }

    @LogOperation(action = "删除作品", target = "'id=' + #id")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        artworkService.delete(id);
        return Result.ok("删除成功");
    }
}
