package top.kaiven.qiming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启明艺术空间 - 启动类
 */
@EnableAsync
@SpringBootApplication
public class QimingApplication {
    public static void main(String[] args) {
        SpringApplication.run(QimingApplication.class, args);
    }
}
