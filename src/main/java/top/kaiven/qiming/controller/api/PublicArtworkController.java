package top.kaiven.qiming.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.kaiven.qiming.common.Result;
import top.kaiven.qiming.entity.Artwork;
import top.kaiven.qiming.service.ArtworkService;

/**
 * 前台作品接口（无需 JWT）
 */
@RestController
@RequestMapping("/api/public/artworks")
@RequiredArgsConstructor
public class PublicArtworkController {

    private final ArtworkService artworkService;

    @GetMapping
    public Result<IPage<Artwork>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(required = false) Long categoryId) {
        return Result.ok(artworkService.pagePublic(page, size, categoryId));
    }

    @GetMapping("/{id}")
    public Result<Artwork> detail(@PathVariable Long id) {
        artworkService.incrementView(id);
        return Result.ok(artworkService.getById(id));
    }
}
