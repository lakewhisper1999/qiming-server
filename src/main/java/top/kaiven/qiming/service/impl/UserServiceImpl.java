package top.kaiven.qiming.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import top.kaiven.qiming.common.BizException;
import top.kaiven.qiming.common.JwtUtils;
import top.kaiven.qiming.dto.LoginDTO;
import top.kaiven.qiming.dto.TokenDTO;
import top.kaiven.qiming.entity.User;
import top.kaiven.qiming.mapper.UserMapper;
import top.kaiven.qiming.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public TokenDTO login(LoginDTO dto) {
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getUsername, dto.getUsername())
        );
        if (user == null) {
            throw BizException.badRequest("用户名或密码错误");
        }
        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            throw BizException.badRequest("用户名或密码错误");
        }
        String token = jwtUtils.generateToken(user.getId(), user.getUsername(), user.getRole());
        return new TokenDTO(token, user.getNickname(), user.getAvatar());
    }

    @Override
    public User getById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public void updateProfile(Long userId, String nickname, String avatar) {
        User user = new User();
        user.setId(userId);
        user.setNickname(nickname);
        user.setAvatar(avatar);
        userMapper.updateById(user);
    }

    @Override
    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.selectById(userId);
        if (!encoder.matches(oldPassword, user.getPassword())) {
            throw BizException.badRequest("原密码错误");
        }
        user.setPassword(encoder.encode(newPassword));
        userMapper.updateById(user);
    }

    @Override
    public List<User> listAll() {
        return userMapper.selectList(null);
    }

    @Override
    public void updateUser(Long userId, String nickname, String role) {
        User user = new User();
        user.setId(userId);
        user.setNickname(nickname);
        user.setRole(role);
        userMapper.updateById(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userMapper.deleteById(userId);
    }

    /** 工具方法：对密码BCrypt加密 */
    public String encodePassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }
}
