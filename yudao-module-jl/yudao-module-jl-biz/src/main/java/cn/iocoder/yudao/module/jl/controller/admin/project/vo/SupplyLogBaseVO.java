package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 项目物资变更日志 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class SupplyLogBaseVO {

    @Schema(description = "相关的id，采购、寄来、取货", requiredMode = Schema.RequiredMode.REQUIRED, example = "2967")
    @NotNull(message = "相关的id，采购、寄来、取货不能为空")
    private Long refId;

    @Schema(description = "类型：采购、寄来、取货", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "类型：采购、寄来、取货不能为空")
    private Long type;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "备注描述")
    private String mark;

    @Schema(description = "变更描述")
    private String log;

}
