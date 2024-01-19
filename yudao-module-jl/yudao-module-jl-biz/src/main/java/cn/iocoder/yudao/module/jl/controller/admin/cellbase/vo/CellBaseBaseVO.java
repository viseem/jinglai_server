package cn.iocoder.yudao.module.jl.controller.admin.cellbase.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 细胞数据 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CellBaseBaseVO {

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotNull(message = "名称不能为空")
    private String name;

    @Schema(description = "数量")
    private String quantity;

    @Schema(description = "传代次数", example = "27831")
    private String generationCount;

    @Schema(description = "项目id", example = "9921")
    private Integer projectId;

    @Schema(description = "客户id", example = "13645")
    private Integer customerId;

    @Schema(description = "备注")
    private String mark;

}
