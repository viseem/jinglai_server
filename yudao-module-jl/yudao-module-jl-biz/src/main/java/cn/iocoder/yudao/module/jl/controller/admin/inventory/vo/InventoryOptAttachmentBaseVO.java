package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 库管操作附件记录 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class InventoryOptAttachmentBaseVO {

    @Schema(description = "项目 id", example = "27687")
    private Long projectId;

    @Schema(description = "类型，采购，寄送，自取", example = "2")
    private String type;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "采购，寄送，自取的 id", example = "28696")
    private Long refId;

    @Schema(description = "采购，寄送，自取的子元素 id", example = "13927")
    private Long refItemId;

    @Schema(description = "名称", example = "张三")
    private String fileName;

    @Schema(description = "地址", example = "https://www.iocoder.cn")
    private String fileUrl;

}
