package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 项目的实验名目更新 Request VO")
@Data
@ToString(callSuper = true)
public class ProjectCategoryUpdateDeletedStatusReqVO {

    @Schema(description = "岗位ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11767")
    @NotNull(message = "岗位ID不能为空")
    private Long id;

    private Boolean deletedStatus;
}
