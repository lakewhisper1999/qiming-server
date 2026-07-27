package top.kaiven.qiming.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.kaiven.qiming.common.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 认证过滤器 — 拦截 /api/admin/* 请求
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtAuthFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        // 登录接口放行
        String path = request.getRequestURI();
        if (path.contains("/api/admin/login")) {
            chain.doFilter(request, response);
            return;
        }

        // 从 Header 获取 Token
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // Token 为空或无效 → 401
        if (token == null || !jwtUtils.validateToken(token)) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"code\":401,\"msg\":\"未登录或Token已过期\",\"data\":null}");
            return;
        }

        // 校验角色是否为 admin
        String role = jwtUtils.getRole(token);
        if (!"admin".equals(role)) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"code\":403,\"msg\":\"无管理员权限\",\"data\":null}");
            return;
        }

        // 将用户信息存入 request attribute，供 Controller 使用
        request.setAttribute("userId", jwtUtils.getUserId(token));
        request.setAttribute("username", jwtUtils.getUsername(token));

        chain.doFilter(request, response);
    }
}
