package cn.iocoder.yudao.module.jl.controller.admin.commontodolog.vo;

import cn.iocoder.yudao.module.jl.enums.CommonTodoEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 通用TODO记录更新 Request VO")
@Data
@ToString(callSuper = true)
public class CommonTodoLogUpdateStatusReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11333")
    @NotNull(message = "ID不能为空")
    private Long id;

    @Schema(description = "todo状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String status;

}
