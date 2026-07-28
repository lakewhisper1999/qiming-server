package top.kaiven.qiming.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.kaiven.qiming.common.BizException;
import top.kaiven.qiming.dto.ArtworkDTO;
import top.kaiven.qiming.entity.Artwork;
import top.kaiven.qiming.mapper.ArtworkMapper;
import top.kaiven.qiming.service.ArtworkService;

@Service
@RequiredArgsConstructor
public class ArtworkServiceImpl implements ArtworkService {

    private final ArtworkMapper artworkMapper;

    @Override
    public IPage<Artwork> pagePublic(int page, int size, Long categoryId) {
        Page<Artwork> pageObj = new Page<>(page, size);
        if (categoryId != null) {
            return artworkMapper.selectPageByCategory(pageObj, categoryId);
        }
        return artworkMapper.selectPageAll(pageObj);
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
        artwork.setTitle(dto.getTitle());
        artwork.setDescription(dto.getDescription());
        artwork.setCoverUrl(dto.getCoverUrl());
        artwork.setImageUrls(dto.getImageUrls());
        artwork.setCategoryId(dto.getCategoryId());
        artwork.setDownloadUrl(dto.getDownloadUrl());
        artwork.setVideoUrl(dto.getVideoUrl());
        artwork.setFileSize(dto.getFileSize() != null ? dto.getFileSize() : 0L);
        artwork.setViewCount(0);
        artwork.setDownloadCount(0);
        artwork.setUserId(userId);
        artworkMapper.insert(artwork);
        return artwork;
    }

    @Override
    public Artwork update(ArtworkDTO dto, Long userId) {
        Artwork artwork = artworkMapper.selectById(dto.getId());
        if (artwork == null) throw BizException.notFound("作品不存在");
        artwork.setTitle(dto.getTitle());
        artwork.setDescription(dto.getDescription());
        artwork.setCoverUrl(dto.getCoverUrl());
        artwork.setImageUrls(dto.getImageUrls());
        artwork.setCategoryId(dto.getCategoryId());
        artwork.setDownloadUrl(dto.getDownloadUrl());
        artwork.setVideoUrl(dto.getVideoUrl());
        artwork.setFileSize(dto.getFileSize() != null ? dto.getFileSize() : artwork.getFileSize());
        artworkMapper.updateById(artwork);
        return artwork;
    }

    @Override
    public void delete(Long id) {
        artworkMapper.deleteById(id);
    }
}
