package top.kaiven.qiming.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * 管理员回复请求
 */
@Data
public class ReplyDTO {
    @NotBlank(message = "回复内容不能为空")
    private String reply;
}
