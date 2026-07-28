package top.kaiven.qiming.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("artwork")
public class Artwork {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String coverUrl;
    private String imageUrls;
    private Long categoryId;
    private String downloadUrl;
    private String videoUrl;
    private Long fileSize;
    private Integer viewCount;
    private Integer downloadCount;
    private Long userId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableLogic
    private Integer deleted;

    // 非表字段：分类名（联表查询用）
    @TableField(exist = false)
    private String categoryName;
}
