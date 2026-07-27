package top.kaiven.qiming.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.kaiven.qiming.common.Result;
import top.kaiven.qiming.dto.CategoryDTO;
import top.kaiven.qiming.entity.Category;
import top.kaiven.qiming.service.CategoryService;

import javax.validation.Valid;
import java.util.List;

/**
 * 后台分类管理（需 JWT + admin role）
 */
@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public Result<List<Category>> list() {
        return Result.ok(categoryService.listAdmin());
    }

    @PostMapping
    public Result<Category> create(@Valid @RequestBody CategoryDTO dto) {
        return Result.ok("新增成功", categoryService.save(dto));
    }

    @PutMapping("/{id}")
    public Result<Category> update(@PathVariable Long id, @Valid @RequestBody CategoryDTO dto) {
        dto.setId(id);
        return Result.ok("编辑成功", categoryService.update(dto));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return Result.ok("删除成功");
    }
}
