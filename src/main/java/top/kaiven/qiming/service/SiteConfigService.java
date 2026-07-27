package top.kaiven.qiming.service;

import top.kaiven.qiming.entity.SiteConfig;
import java.util.List;

public interface SiteConfigService {
    List<SiteConfig> listAll();
    void updateBatch(List<SiteConfig> configs);
}
