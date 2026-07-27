package top.kaiven.qiming.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.kaiven.qiming.common.Result;
import top.kaiven.qiming.dto.LoginDTO;
import top.kaiven.qiming.dto.TokenDTO;
import top.kaiven.qiming.service.UserService;

import javax.validation.Valid;

/**
 * 认证控制器 — 管理员登录
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public Result<TokenDTO> login(@Valid @RequestBody LoginDTO dto) {
        return Result.ok(userService.login(dto));
    }
}
