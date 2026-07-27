package top.kaiven.qiming.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    // ---- 成功 ----
    public static <T> Result<T> ok() {
        return new Result<>(200, "success", null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(200, "success", data);
    }

    public static <T> Result<T> ok(String msg, T data) {
        return new Result<>(200, msg, data);
    }

    // ---- 失败 ----
    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(400, msg, null);
    }

    // ---- 401 未授权 ----
    public static <T> Result<T> unauthorized(String msg) {
        return new Result<>(401, msg, null);
    }

    // ---- 403 无权限 ----
    public static <T> Result<T> forbidden(String msg) {
        return new Result<>(403, msg, null);
    }
}
