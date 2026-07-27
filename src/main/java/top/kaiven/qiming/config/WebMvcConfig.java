package top.kaiven.qiming.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * WebMvc 配置 — 跨域 + 静态资源
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.access-path}")
    private String accessPath;

    /**
     * 跨域：允许前台(5173)和后台(5174)访问
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    /**
     * 静态资源映射：上传文件可通过 /uploads/xxx 直接访问
     * 使用绝对路径，避免相对路径解析到 Tomcat 临时目录
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将配置的 uploadPath 转为绝对路径
        Path absPath = Paths.get(uploadPath).toAbsolutePath().normalize();
        String resourceLocation = "file:" + absPath.toString() + "/";
        registry.addResourceHandler(accessPath)
                .addResourceLocations(resourceLocation);
    }
}
