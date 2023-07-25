package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.validation.constraints.*;

/**
 * 动物笼位 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class AnimalBoxBaseVO {

    @Schema(description = "名称", example = "王五")
    private String name;

    @Schema(description = "编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "编码不能为空")
    private String code;

    @Schema(description = "位置")
    private String location;

    @Schema(description = "容量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "容量不能为空")
    private Integer capacity;

    @Schema(description = "现有")
    private Integer quantity;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "架子id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17453")
    @NotNull(message = "架子id不能为空")
    private Long shelfId;

    @Schema(description = "房间id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9431")
    @NotNull(message = "房间id不能为空")
    private Long roomId;

    @Schema(description = "行索引")
    private Integer rowIndex;

    @Schema(description = "列索引")
    private Integer colIndex;

    @Schema(description = "饲养单")
    private Long feedOrderId;

    private String feedOrderCode;

    private String feedOrderName;

    private String projectName;

    private String customerName;

}
