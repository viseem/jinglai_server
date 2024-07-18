package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 项目中的实验名目的收费项更新 Request VO")
@Data
@ToString(callSuper = true)
public class ProjectChargeitemUpdateDeleteStatusReqVO   {

    @Schema(description = "岗位ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17953")
    @NotNull(message = "岗位ID不能为空")
    private Long id;

    private Boolean deletedStatus=true;


}
