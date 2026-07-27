package top.kaiven.qiming.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * 提交提问请求
 */
@Data
public class CommentDTO {
    private String nickname;

    @NotBlank(message = "提问内容不能为空")
    private String content;
}
