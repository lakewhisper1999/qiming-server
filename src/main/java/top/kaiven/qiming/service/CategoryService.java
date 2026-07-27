package top.kaiven.qiming.service;

import top.kaiven.qiming.dto.CategoryDTO;
import top.kaiven.qiming.entity.Category;
import java.util.List;

public interface CategoryService {
    List<Category> listAll();
    List<Category> listAdmin();
    Category save(CategoryDTO dto);
    Category update(CategoryDTO dto);
    void delete(Long id);
}
