package top.kaiven.qiming.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.kaiven.qiming.common.BizException;
import top.kaiven.qiming.dto.CategoryDTO;
import top.kaiven.qiming.entity.Category;
import top.kaiven.qiming.mapper.CategoryMapper;
import top.kaiven.qiming.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Override
    public List<Category> listAll() {
        return categoryMapper.selectList(
                new LambdaQueryWrapper<Category>().orderByAsc(Category::getSortOrder)
        );
    }

    @Override
    public List<Category> listAdmin() {
        return categoryMapper.selectList(
                new LambdaQueryWrapper<Category>().orderByAsc(Category::getSortOrder)
        );
    }

    @Override
    public Category save(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setIcon(dto.getIcon());
        category.setSortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : 0);
        categoryMapper.insert(category);
        return category;
    }

    @Override
    public Category update(CategoryDTO dto) {
        Category category = categoryMapper.selectById(dto.getId());
        if (category == null) throw BizException.notFound("分类不存在");
        category.setName(dto.getName());
        category.setIcon(dto.getIcon());
        if (dto.getSortOrder() != null) category.setSortOrder(dto.getSortOrder());
        categoryMapper.updateById(category);
        return category;
    }

    @Override
    public void delete(Long id) {
        categoryMapper.deleteById(id);
    }
}
