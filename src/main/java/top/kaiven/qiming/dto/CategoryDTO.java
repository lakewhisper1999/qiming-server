package top.kaiven.qiming.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * 分类创建/更新请求
 */
@Data
public class CategoryDTO {
    private Long id;
    @NotBlank(message = "分类名称不能为空")
    private String name;
    private String icon;
    private Integer sortOrder;
}
