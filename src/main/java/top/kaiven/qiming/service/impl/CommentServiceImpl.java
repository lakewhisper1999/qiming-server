package top.kaiven.qiming.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.kaiven.qiming.common.BizException;
import top.kaiven.qiming.dto.CommentDTO;
import top.kaiven.qiming.entity.Comment;
import top.kaiven.qiming.mapper.CommentMapper;
import top.kaiven.qiming.service.CommentService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    @Override
    public IPage<Comment> pagePublic(int page, int size) {
        return commentMapper.selectPage(
                new Page<>(page, size),
                new LambdaQueryWrapper<Comment>()
                        .orderByDesc(Comment::getCreatedAt)
        );
    }

    @Override
    public Comment submit(CommentDTO dto) {
        Comment comment = new Comment();
        comment.setNickname(dto.getNickname() != null ? dto.getNickname() : "匿名用户");
        comment.setContent(dto.getContent());
        commentMapper.insert(comment);
        return comment;
    }

    @Override
    public IPage<Comment> pageAdmin(int page, int size) {
        return commentMapper.selectPage(
                new Page<>(page, size),
                new LambdaQueryWrapper<Comment>()
                        .orderByDesc(Comment::getCreatedAt)
        );
    }

    @Override
    public Comment reply(Long id, String replyContent, Long userId) {
        Comment comment = commentMapper.selectById(id);
        if (comment == null) throw BizException.notFound("提问不存在");
        comment.setReply(replyContent);
        comment.setUserId(userId);
        comment.setRepliedAt(LocalDateTime.now());
        commentMapper.updateById(comment);
        return comment;
    }

    @Override
    public void delete(Long id) {
        commentMapper.deleteById(id);
    }
}
