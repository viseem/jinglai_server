package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 实验产品入库 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProductInBaseVO {

    @Schema(description = "项目 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14297")
    @NotNull(message = "项目 id不能为空")
    private Long projectId;

    @Schema(description = "实验名目库的名目 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "25595")
    @NotNull(message = "实验名目库的名目 id不能为空")
    private Long categoryId;

    @Schema(description = "实验名目 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "29527")
    @NotNull(message = "实验名目 id不能为空")
    private Long projectCategoryId;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "备注")
    private String mark;

}