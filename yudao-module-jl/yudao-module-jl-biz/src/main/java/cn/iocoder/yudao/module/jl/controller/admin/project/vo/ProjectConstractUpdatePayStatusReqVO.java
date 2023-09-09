package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 项目合同更新 Request VO")
@Data

@ToString(callSuper = true)
public class ProjectConstractUpdatePayStatusReqVO {

    @Schema(description = "岗位ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22660")
    @NotNull(message = "岗位ID不能为空")
    private Long id;

    @Schema(description = "支付状态", example = "芋艿")
    private String payStatus;
}
