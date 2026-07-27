package top.kaiven.qiming.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.kaiven.qiming.dto.ArtworkDTO;
import top.kaiven.qiming.entity.Artwork;

public interface ArtworkService {
    IPage<Artwork> pagePublic(int page, int size, Long categoryId);
    Artwork getById(Long id);
    void incrementView(Long id);
    IPage<Artwork> pageAdmin(int page, int size, String keyword, Long categoryId);
    Artwork save(ArtworkDTO dto, Long userId);
    Artwork update(ArtworkDTO dto, Long userId);
    void delete(Long id);
}
