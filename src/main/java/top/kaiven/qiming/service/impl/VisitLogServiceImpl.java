package top.kaiven.qiming.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.kaiven.qiming.entity.VisitLog;
import top.kaiven.qiming.mapper.VisitLogMapper;
import top.kaiven.qiming.service.VisitLogService;

@Service
@RequiredArgsConstructor
public class VisitLogServiceImpl implements VisitLogService {

    private final VisitLogMapper visitLogMapper;

    @Override
    public void record(VisitLog log) {
        visitLogMapper.insert(log);
    }
}
