package top.kaiven.qiming.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.kaiven.qiming.common.Result;
import top.kaiven.qiming.dto.CommentDTO;
import top.kaiven.qiming.entity.Comment;
import top.kaiven.qiming.service.CommentService;

import javax.validation.Valid;

/**
 * 前台提问箱接口（无需 JWT，匿名提交）
 */
@RestController
@RequestMapping("/api/public/comments")
@RequiredArgsConstructor
public class PublicCommentController {

    private final CommentService commentService;

    @GetMapping
    public Result<IPage<Comment>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.ok(commentService.pagePublic(page, size));
    }

    @PostMapping
    public Result<Comment> submit(@Valid @RequestBody CommentDTO dto) {
        return Result.ok("提问成功", commentService.submit(dto));
    }
}
