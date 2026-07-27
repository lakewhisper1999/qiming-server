package top.kaiven.qiming.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.kaiven.qiming.common.Result;
import top.kaiven.qiming.dto.PasswordDTO;
import top.kaiven.qiming.dto.ProfileDTO;
import top.kaiven.qiming.entity.User;
import top.kaiven.qiming.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 后台用户管理（需 JWT + admin role）
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /** 获取当前用户信息 */
    @GetMapping("/profile")
    public Result<User> profile(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.ok(userService.getById(userId));
    }

    /** 修改个人信息 */
    @PutMapping("/profile")
    public Result<?> updateProfile(@Valid @RequestBody ProfileDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        userService.updateProfile(userId, dto.getNickname(), dto.getAvatar());
        return Result.ok("修改成功");
    }

    /** 修改密码 */
    @PutMapping("/password")
    public Result<?> updatePassword(@Valid @RequestBody PasswordDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        userService.updatePassword(userId, dto.getOldPassword(), dto.getNewPassword());
        return Result.ok("密码修改成功");
    }

    // ==================== 用户管理 CRUD ====================

    /** 获取所有用户列表 */
    @GetMapping("/users")
    public Result<List<User>> listUsers() {
        return Result.ok(userService.listAll());
    }

    /** 编辑用户（昵称 + 角色） */
    @PutMapping("/users/{id}")
    public Result<?> updateUser(@PathVariable Long id, @Valid @RequestBody ProfileDTO dto) {
        userService.updateUser(id, dto.getNickname(), dto.getRole());
        return Result.ok("修改成功");
    }

    /** 删除用户（逻辑删除） */
    @DeleteMapping("/users/{id}")
    public Result<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return Result.ok("删除成功");
    }
}
