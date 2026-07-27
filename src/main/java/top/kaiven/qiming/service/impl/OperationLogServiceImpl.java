package top.kaiven.qiming.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.kaiven.qiming.entity.OperationLog;
import top.kaiven.qiming.mapper.OperationLogMapper;
import top.kaiven.qiming.service.OperationLogService;

@Service
@RequiredArgsConstructor
public class OperationLogServiceImpl implements OperationLogService {

    private final OperationLogMapper operationLogMapper;

    @Override
    public void record(OperationLog log) {
        operationLogMapper.insert(log);
    }

    @Override
    public IPage<OperationLog> page(int page, int size) {
        return operationLogMapper.selectPage(
                new Page<>(page, size),
                new LambdaQueryWrapper<OperationLog>()
                        .orderByDesc(OperationLog::getCreatedAt)
        );
    }
}
