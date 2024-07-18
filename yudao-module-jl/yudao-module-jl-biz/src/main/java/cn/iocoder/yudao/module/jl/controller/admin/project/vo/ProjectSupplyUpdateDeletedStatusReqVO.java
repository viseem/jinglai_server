package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 项目中的实验名目的物资项更新 Request VO")
@Data
@ToString(callSuper = true)
public class ProjectSupplyUpdateDeletedStatusReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31521")
    @NotNull(message = "ID不能为空")
    private Long id;

    private Boolean deletedStatus=true;
}
