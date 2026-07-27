package top.kaiven.qiming.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.kaiven.qiming.annotation.LogOperation;
import top.kaiven.qiming.entity.OperationLog;
import top.kaiven.qiming.service.OperationLogService;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * AOP 切面 — 统一记录管理后台操作日志。
 * 消除 ArtworkController / ArticleController / CommentController 中的 3 处重复代码。
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final OperationLogService operationLogService;

    @AfterReturning("@annotation(top.kaiven.qiming.annotation.LogOperation)")
    public void afterReturning(JoinPoint joinPoint) {
        try {
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs == null) return;
            HttpServletRequest request = attrs.getRequest();

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            LogOperation anno = method.getAnnotation(LogOperation.class);

            Long userId = (Long) request.getAttribute("userId");
            String username = (String) request.getAttribute("username");
            if (userId == null || username == null) return;

            OperationLog ol = new OperationLog();
            ol.setUserId(userId);
            ol.setUsername(username);
            ol.setAction(anno.action());
            ol.setTarget(anno.target());
            ol.setIp(getIp(request));
            operationLogService.record(ol);
        } catch (Exception e) {
            // 日志记录失败不应影响主业务
            log.warn("操作日志记录失败: {}", e.getMessage());
        }
    }

    private String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
