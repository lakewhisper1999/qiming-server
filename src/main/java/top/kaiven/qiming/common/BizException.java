package top.kaiven.qiming.common;

import lombok.Getter;

/**
 * 业务异常 — 替代直接 throw new RuntimeException。
 * 由 GlobalExceptionHandler 统一捕获并返回 {code, msg} 给前端。
 */
@Getter
public class BizException extends RuntimeException {

    private final int code;

    public BizException(String msg) {
        super(msg);
        this.code = 400;
    }

    public BizException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    /** 资源不存在（404） */
    public static BizException notFound(String msg) {
        return new BizException(404, msg);
    }

    /** 参数校验失败（400） */
    public static BizException badRequest(String msg) {
        return new BizException(400, msg);
    }

    /** 权限不足（403） */
    public static BizException forbidden(String msg) {
        return new BizException(403, msg);
    }
}
