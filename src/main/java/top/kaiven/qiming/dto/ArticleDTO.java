package top.kaiven.qiming.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * 文章创建/更新请求
 */
@Data
public class ArticleDTO {
    private Long id;
    @NotBlank(message = "标题不能为空")
    private String title;
    private String content;
    private String coverUrl;
    private Long categoryId;
}
