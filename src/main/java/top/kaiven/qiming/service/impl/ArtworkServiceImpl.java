package top.kaiven.qiming.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.kaiven.qiming.common.BizException;
import top.kaiven.qiming.dto.ArtworkDTO;
import top.kaiven.qiming.entity.Artwork;
import top.kaiven.qiming.mapper.ArtworkMapper;
import top.kaiven.qiming.service.ArtworkService;
import top.kaiven.qiming.common.PublishService;

@Service
@RequiredArgsConstructor
public class ArtworkServiceImpl implements ArtworkService {

    private final ArtworkMapper artworkMapper;
    private final PublishService publishService;

    @Override
    public IPage<Artwork> pagePublic(int page, int size, Long categoryId) {
        return artworkMapper.selectPagePublic(new Page<>(page, size), categoryId);
    }

    @Override
    public Artwork getById(Long id) {
        return artworkMapper.selectById(id);
    }

    @Override
    public void incrementView(Long id) {
        artworkMapper.incrementView(id);
    }

    @Override
    public IPage<Artwork> pageAdmin(int page, int size, String keyword, Long categoryId) {
        return artworkMapper.selectPageAdmin(
                new Page<>(page, size),
                StringUtils.hasText(keyword) ? keyword : null,
                categoryId);
    }

    @Override
    public Artwork save(ArtworkDTO dto, Long userId) {
        Artwork artwork = new Artwork();
        BeanUtils.copyProperties(dto, artwork);
        artwork.setUserId(userId);
        artwork.setViewCount(0);
        artwork.setDownloadCount(0);
        artwork.setFileSize(dto.getFileSize() != null ? dto.getFileSize() : 0L);
        artworkMapper.insert(artwork);
        publishService.publishAsync();
        return artwork;
    }

    @Override
    public Artwork update(ArtworkDTO dto, Long userId) {
        Artwork artwork = artworkMapper.selectById(dto.getId());
        if (artwork == null) throw BizException.notFound("作品不存在");
        // 复制可编辑字段；忽略 id 与 fileSize：fileSize 为空时保留原值，避免被 null 覆盖
        BeanUtils.copyProperties(dto, artwork, "id", "fileSize");
        if (dto.getFileSize() != null) {
            artwork.setFileSize(dto.getFileSize());
        }
        artworkMapper.updateById(artwork);
        publishService.publishAsync();
        return artwork;
    }

    @Override
    public void delete(Long id) {
        artworkMapper.deleteById(id);
        publishService.publishAsync();
    }
}
