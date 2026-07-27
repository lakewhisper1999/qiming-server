package top.kaiven.qiming.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.kaiven.qiming.common.Result;
import top.kaiven.qiming.entity.OperationLog;
import top.kaiven.qiming.entity.VisitLog;
import top.kaiven.qiming.mapper.VisitLogMapper;
import top.kaiven.qiming.service.OperationLogService;

/**
 * 后台日志管理（需 JWT + admin role）
 */
@RestController
@RequestMapping("/api/admin/logs")
@RequiredArgsConstructor
public class LogController {

    private final VisitLogMapper visitLogMapper;
    private final OperationLogService operationLogService;

    @GetMapping("/visits")
    public Result<IPage<VisitLog>> visits(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return Result.ok(visitLogMapper.selectPage(
                new Page<>(page, size),
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<VisitLog>()
                        .orderByDesc(VisitLog::getCreatedAt)
        ));
    }

    @GetMapping("/operations")
    public Result<IPage<OperationLog>> operations(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return Result.ok(operationLogService.page(page, size));
    }
}
