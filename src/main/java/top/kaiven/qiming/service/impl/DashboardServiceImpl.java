package top.kaiven.qiming.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.kaiven.qiming.dto.DashboardDTO;
import top.kaiven.qiming.mapper.*;
import top.kaiven.qiming.service.DashboardService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final ArtworkMapper artworkMapper;
    private final ArticleMapper articleMapper;
    private final CommentMapper commentMapper;
    private final VisitLogMapper visitLogMapper;

    @Override
    public DashboardDTO getStats() {
        long artworkCount = artworkMapper.selectCount(null);
        long articleCount = articleMapper.selectCount(null);
        long commentCount = commentMapper.selectCount(null);

        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        long todayVisits = visitLogMapper.selectCount(
                new LambdaQueryWrapper<top.kaiven.qiming.entity.VisitLog>()
                        .ge(top.kaiven.qiming.entity.VisitLog::getCreatedAt, todayStart)
        );
        long totalVisits = visitLogMapper.selectCount(null);

        return new DashboardDTO(artworkCount, articleCount, commentCount, todayVisits, totalVisits);
    }
}
