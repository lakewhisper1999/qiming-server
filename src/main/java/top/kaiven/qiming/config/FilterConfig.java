package top.kaiven.qiming.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.kaiven.qiming.filter.JwtAuthFilter;

/**
 * Filter 注册 — JWT 认证过滤器
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtFilter(JwtAuthFilter filter) {
        FilterRegistrationBean<JwtAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.addUrlPatterns("/api/admin/*");  // 只拦截后台接口
        registration.setOrder(1);
        return registration;
    }
}
