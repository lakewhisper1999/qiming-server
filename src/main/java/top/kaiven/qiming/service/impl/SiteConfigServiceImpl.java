package top.kaiven.qiming.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.kaiven.qiming.entity.SiteConfig;
import top.kaiven.qiming.mapper.SiteConfigMapper;
import top.kaiven.qiming.service.SiteConfigService;
import top.kaiven.qiming.common.PublishService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SiteConfigServiceImpl implements SiteConfigService {

    private final SiteConfigMapper siteConfigMapper;
    private final PublishService publishService;

    @Override
    public List<SiteConfig> listAll() {
        return siteConfigMapper.selectList(null);
    }

    @Override
    public void updateBatch(List<SiteConfig> configs) {
        for (SiteConfig config : configs) {
            SiteConfig exist = siteConfigMapper.selectOne(
                new LambdaQueryWrapper<SiteConfig>().eq(SiteConfig::getConfigKey, config.getConfigKey())
            );
            if (exist != null) {
                config.setId(exist.getId());
                siteConfigMapper.updateById(config);
            } else {
                siteConfigMapper.insert(config);
            }
        }
        publishService.publishAsync();
    }
}
