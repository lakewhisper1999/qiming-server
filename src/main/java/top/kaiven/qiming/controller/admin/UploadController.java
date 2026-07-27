package top.kaiven.qiming.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.kaiven.qiming.common.Result;
import top.kaiven.qiming.service.UploadService;

import java.util.Map;

/**
 * 后台文件上传（需 JWT + admin role）
 */
@RestController
@RequestMapping("/api/admin/upload")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping
    public Result<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
        return Result.ok("上传成功", uploadService.uploadFile(file));
    }
}
