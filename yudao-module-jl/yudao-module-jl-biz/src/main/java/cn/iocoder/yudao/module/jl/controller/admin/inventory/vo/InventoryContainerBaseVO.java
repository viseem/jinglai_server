package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 库管存储容器 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class InventoryContainerBaseVO {

    @Schema(description = "库房id：可以用字典", requiredMode = Schema.RequiredMode.REQUIRED, example = "26015")
    @NotNull(message = "库房id：可以用字典不能为空")
    private Integer roomId;

    @Schema(description = "名称", example = "张三")
    private String name;

    @Schema(description = "所在位置")
    private String place;

    @Schema(description = "负责人", example = "1556")
    private String guardianUserId;

    @Schema(description = "备注描述")
    private String mark;

}
