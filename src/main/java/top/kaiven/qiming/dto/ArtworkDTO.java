package top.kaiven.qiming.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * 作品创建/更新请求
 */
@Data
public class ArtworkDTO {
    private Long id;
    @NotBlank(message = "标题不能为空")
    private String title;
    private String description;
    @NotBlank(message = "封面不能为空")
    private String coverUrl;
    private String imageUrls;
    private Long categoryId;
    private String downloadUrl;
    private Long fileSize;
}
