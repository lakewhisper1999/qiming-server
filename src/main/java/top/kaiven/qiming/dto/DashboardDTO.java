package top.kaiven.qiming.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 仪表盘统计数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {
    private long artworkCount;
    private long articleCount;
    private long commentCount;
    private long todayVisits;   // 今日访问（匹配前端 stats.todayVisits）
    private long totalVisits;     // 总访问
}
