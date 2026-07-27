package top.kaiven.qiming.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kaiven.qiming.common.Result;
import top.kaiven.qiming.dto.DashboardDTO;
import top.kaiven.qiming.entity.Artwork;
import top.kaiven.qiming.entity.Comment;
import top.kaiven.qiming.service.ArtworkService;
import top.kaiven.qiming.service.CommentService;
import top.kaiven.qiming.service.DashboardService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台仪表盘（需 JWT + admin role）
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final ArtworkService artworkService;
    private final CommentService commentService;

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> dashboard() {
        DashboardDTO stats = dashboardService.getStats();

        // 最近 5 件作品
        IPage<Artwork> artworkPage = artworkService.pagePublic(1, 5, null);
        List<Artwork> recentArtworks = artworkPage.getRecords();

        // 最近 5 条提问
        IPage<Comment> commentPage = commentService.pagePublic(1, 5);
        List<Comment> recentComments = commentPage.getRecords();

        Map<String, Object> data = new HashMap<>();
        data.put("stats", stats);
        data.put("recentArtworks", recentArtworks);
        data.put("recentComments", recentComments);

        return Result.ok(data);
    }
}
