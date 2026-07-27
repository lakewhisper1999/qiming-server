package top.kaiven.qiming.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录成功返回 Token
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    private String token;
    private String nickname;
    private String avatar;
}
