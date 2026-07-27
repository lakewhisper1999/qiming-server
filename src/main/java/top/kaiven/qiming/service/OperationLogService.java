package top.kaiven.qiming.service;

import top.kaiven.qiming.entity.OperationLog;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface OperationLogService {
    void record(OperationLog log);
    IPage<OperationLog> page(int page, int size);
}
