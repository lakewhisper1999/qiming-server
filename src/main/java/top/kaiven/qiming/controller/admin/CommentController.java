package top.kaiven.qiming.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.kaiven.qiming.annotation.LogOperation;
import top.kaiven.qiming.common.Result;
import top.kaiven.qiming.dto.ReplyDTO;
import top.kaiven.qiming.entity.Comment;
import top.kaiven.qiming.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 后台提问管理（需 JWT + admin role）
 * 操作日志由 @LogOperation + LogAspect 统一处理。
 */
@RestController
@RequestMapping("/api/admin/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public Result<IPage<Comment>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.ok(commentService.pageAdmin(page, size));
    }

    @LogOperation(action = "回复提问", target = "'id=' + #id")
    @PutMapping("/{id}")
    public Result<Comment> reply(@PathVariable Long id, @Valid @RequestBody ReplyDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Comment comment = commentService.reply(id, dto.getReply(), userId);
        return Result.ok("回复成功", comment);
    }

    @LogOperation(action = "删除提问", target = "'id=' + #id")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        commentService.delete(id);
        return Result.ok("删除成功");
    }
}
