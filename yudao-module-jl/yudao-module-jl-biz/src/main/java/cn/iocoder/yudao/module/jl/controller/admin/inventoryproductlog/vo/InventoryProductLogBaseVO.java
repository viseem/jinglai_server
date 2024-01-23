package cn.iocoder.yudao.module.jl.controller.admin.inventoryproductlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 产品变更日志 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class InventoryProductLogBaseVO {

    @Schema(description = "名称", example = "王五")
    private String name;

    @Schema(description = "样本id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14816")
    @NotNull(message = "样本id不能为空")
    private Integer productItemId;

    @Schema(description = "位置")
    private String location;

    @Schema(description = "数量")
    private Integer quantity;

    @Schema(description = "说明")
    private String mark;

    @Schema(description = "实验人员id", example = "11711")
    private Long experId;

}
