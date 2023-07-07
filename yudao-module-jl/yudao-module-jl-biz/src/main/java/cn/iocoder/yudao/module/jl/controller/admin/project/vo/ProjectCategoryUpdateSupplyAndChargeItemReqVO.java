package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.entity.project.ProjectChargeitem;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSupply;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "管理后台 - 项目的实验名目创建 Request VO")
@Data
@ToString(callSuper = true)
public class ProjectCategoryUpdateSupplyAndChargeItemReqVO {
    @Schema(description = "实验名目 id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long categoryId;

    @Schema(description = "projectCategoryId id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long projectCategoryId;

    @Schema(description = "scheduleId id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long scheduleId;

    @Schema(description = "projectId id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long projectId;

    private List<ProjectChargeitem> chargeList = new ArrayList<>();
    private List<ProjectSupply> supplyList = new ArrayList<>();
}
