package top.kaiven.qiming.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.kaiven.qiming.common.BizException;
import org.springframework.web.multipart.MultipartFile;
import top.kaiven.qiming.service.UploadService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.access-path}")
    private String accessPath;

    /** 允许的文件扩展名（白名单） */
    private static final String[] ALLOWED_EXTENSIONS = {
        // 图片
        ".png", ".jpg", ".jpeg", ".gif", ".webp", ".avif", ".svg", ".bmp",
        // 视频
        ".mp4", ".webm", ".ogg", ".mov", ".avi", ".mkv",
        // 其他
        ".pdf", ".zip", ".rar"
    };

    @Override
    public Map<String, String> uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw BizException.badRequest("文件为空");
        }

        // 校验文件扩展名
        String originalName = file.getOriginalFilename();
        if (originalName == null) {
            throw BizException.badRequest("文件名无效");
        }
        String ext = "";
        int dotIdx = originalName.lastIndexOf('.');
        if (dotIdx >= 0) {
            ext = originalName.substring(dotIdx).toLowerCase();
        }
        if (!isAllowedExtension(ext)) {
            throw BizException.badRequest("不支持的文件格式：" + ext + "，仅支持图片、视频等常见格式");
        }

        try {
            // 解析为绝对路径，避免 transferTo() 的 Tomcat 相对路径坑
            Path uploadDir = Paths.get(uploadPath).toAbsolutePath().normalize();
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // 生成唯一文件名（保留扩展名）
            String newFileName = UUID.randomUUID().toString().replace("-", "") + ext;

            // 用 InputStream 手动 copy，彻底绕过 transferTo() 的坑
            Path targetPath = uploadDir.resolve(newFileName);
            try (var in = file.getInputStream()) {
                Files.copy(in, targetPath);
            }

            Map<String, String> result = new HashMap<>();
            result.put("url", "/uploads/" + newFileName);
            result.put("fileName", newFileName);
            result.put("originalName", originalName);
            return result;
        } catch (IOException e) {
            throw new BizException("文件上传失败: " + e.getMessage());
        }
    }

    private boolean isAllowedExtension(String ext) {
        for (String allowed : ALLOWED_EXTENSIONS) {
            if (allowed.equalsIgnoreCase(ext)) {
                return true;
            }
        }
        return false;
    }
}
