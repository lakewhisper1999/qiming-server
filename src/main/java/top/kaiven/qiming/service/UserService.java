package top.kaiven.qiming.service;

import top.kaiven.qiming.dto.LoginDTO;
import top.kaiven.qiming.dto.TokenDTO;
import top.kaiven.qiming.entity.User;

import java.util.List;

public interface UserService {
    TokenDTO login(LoginDTO dto);
    User getById(Long id);
    List<User> listAll();
    void updateProfile(Long userId, String nickname, String avatar);
    void updateUser(Long userId, String nickname, String role);
    void deleteUser(Long userId);
    void updatePassword(Long userId, String oldPassword, String newPassword);
}
