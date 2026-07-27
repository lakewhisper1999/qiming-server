package top.kaiven.qiming.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 个人资料更新 DTO
 */
@Data
public class ProfileDTO {

    @NotBlank(message = "昵称不能为空")
    @Size(max = 50, message = "昵称最长50个字符")
    private String nickname;

    private String avatar;

    private String role;  // 管理员编辑用户时可选传 role
}
