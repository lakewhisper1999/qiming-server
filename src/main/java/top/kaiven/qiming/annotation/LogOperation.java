package top.kaiven.qiming.annotation;

import java.lang.annotation.*;

/**
 * AOP 操作日志注解 — 标注在 Controller 方法上自动记录操作日志。
 * 从 JwtAuthFilter 设置的 request attributes 中获取 userId/username。
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogOperation {

    /** 操作类型，如 "新增作品"、"删除提问" */
    String action();

    /** 操作目标表达式，支持 SpEL，如 "'id=' + #id" */
    String target() default "";
}
