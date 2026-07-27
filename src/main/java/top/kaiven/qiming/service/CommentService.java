package top.kaiven.qiming.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.kaiven.qiming.dto.CommentDTO;
import top.kaiven.qiming.entity.Comment;

public interface CommentService {
    IPage<Comment> pagePublic(int page, int size);
    Comment submit(CommentDTO dto);
    IPage<Comment> pageAdmin(int page, int size);
    Comment reply(Long id, String replyContent, Long userId);
    void delete(Long id);
}
