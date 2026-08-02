package top.kaiven.qiming.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 后台保存数据后，自动重新导出前端静态快照（works.json + media 图片）。
 *
 * 设计要点：
 * - 默认关闭（qiming.publish.enabled=false），需显式开启，避免开源仓库误触发。
 * - 异步执行（@Async），绝不影响后台保存接口的响应与事务。
 * - 并发保护：同一时刻只跑一次导出。
 * - 只负责「重新生成快照」这一步；git 提交与推送由开发者手动完成，
 *   因此本服务不触碰 git，也不需要 git 凭据 / SSH 远程。
 * - 任何异常都被吞掉并记录日志，绝不抛给调用方（保存接口照常成功）。
 */
@Slf4j
@Service
public class PublishService {

    @Value("${qiming.publish.enabled:false}")
    private boolean enabled;

    @Value("${qiming.publish.artspace-path:}")
    private String artspacePath;

    @Value("${qiming.publish.node-bin:node}")
    private String nodeBin;

    private final AtomicBoolean running = new AtomicBoolean(false);

    @Async
    public void publishAsync() {
        if (!enabled) {
            log.debug("[publish] 未启用（qiming.publish.enabled=false），跳过");
            return;
        }
        if (!running.compareAndSet(false, true)) {
            log.info("[publish] 上一次仍在执行，跳过本次");
            return;
        }
        try {
            Path base = resolveBase();
            if (base == null || !Files.isDirectory(base)) {
                log.warn("[publish] 前端仓库目录不存在或不可读: {}", artspacePath);
                return;
            }

            // 重新导出 works.json + 复制 media（不碰 git）
            int code = exec(base, nodeBin, "scripts/export-works.mjs");
            if (code != 0) {
                log.error("[publish] 导出失败，export-works.mjs 退出码 {}", code);
                return;
            }

            log.info("[publish] ✓ 已重新生成 works.json / media。请到 qiming-artspace 目录手动 commit + push 触发 Cloudflare 部署");
        } catch (Exception e) {
            log.error("[publish] 异常: {}", e.getMessage(), e);
        } finally {
            running.set(false);
        }
    }

    /** 解析前端仓库目录：绝对路径直接用，相对路径基于进程工作目录（qiming-server）解析 */
    private Path resolveBase() {
        if (artspacePath == null || artspacePath.isBlank()) {
            return null;
        }
        Path p = Path.of(artspacePath);
        if (!p.isAbsolute()) {
            p = Path.of(System.getProperty("user.dir")).resolve(p).normalize();
        }
        return p;
    }

    /** 执行命令，记录输出并返回退出码 */
    private int exec(Path base, String... cmd) throws IOException, InterruptedException {
        log.info("[publish] 执行: {}", String.join(" ", cmd));
        Process p = new ProcessBuilder(cmd)
                .directory(base.toFile())
                .redirectErrorStream(true)
                .start();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader r = new BufferedReader(
                new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        int code = p.waitFor();
        if (!sb.toString().isBlank()) {
            log.info("[publish] 输出:\n{}", sb);
        }
        return code;
    }
}
