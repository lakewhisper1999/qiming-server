package top.kaiven.qiming.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import top.kaiven.qiming.entity.User;
import top.kaiven.qiming.mapper.UserMapper;

/**
 * 数据初始化 — 首次启动时创建默认管理员
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserMapper userMapper;

    @Override
    public void run(String... args) {
        if (userMapper.selectCount(null) == 0) {
            String encodedPwd = new BCryptPasswordEncoder().encode("admin123");
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(encodedPwd);
            admin.setNickname("管理员");
            admin.setRole("admin");
            userMapper.insert(admin);
            log.info("========== 默认管理员已创建 ==========");
            log.info("  用户名: admin");
            log.info("  密  码: admin123");
            log.info("=====================================");
        }
    }
}
