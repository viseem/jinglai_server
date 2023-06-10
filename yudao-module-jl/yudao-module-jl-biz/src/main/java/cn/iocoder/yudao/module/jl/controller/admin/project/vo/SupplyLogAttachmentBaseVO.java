package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 库管项目物资库存变更日志附件 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class SupplyLogAttachmentBaseVO {

    @Schema(description = "项目物资日志表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24682")
    @NotNull(message = "项目物资日志表id不能为空")
    private Long projectSupplyLogId;

    @Schema(description = "附件名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "附件名称不能为空")
    private String fileName;

    @Schema(description = "附件地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotNull(message = "附件地址不能为空")
    private String fileUrl;

}
