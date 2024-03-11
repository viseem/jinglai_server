package cn.iocoder.yudao.module.jl.controller.admin.picollaborationitem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import javax.validation.constraints.*;

/**
 * pi组协作明细 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class PiCollaborationItemBaseVO {

    @Schema(description = "协助的主表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8954")
    @NotNull(message = "协助的主表id不能为空")
    private Long collaborationId;

    @Schema(description = "pi组id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6278")
    @NotNull(message = "pi组id不能为空")
    private Long piId;

    @Schema(description = "占比")
    private BigDecimal percent;

    @Schema(description = "备注")
    private String mark;

}
