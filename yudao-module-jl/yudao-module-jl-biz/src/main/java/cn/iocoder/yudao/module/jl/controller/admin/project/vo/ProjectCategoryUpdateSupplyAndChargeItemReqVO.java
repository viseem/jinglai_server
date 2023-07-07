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
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectCategoryUpdateSupplyAndChargeItemReqVO extends ProjectCategoryBaseVO {
    @Schema(description = "项目实验 id", example = "1")
    private Long projectCategoryId;

    private List<ProjectChargeitemCreateReqVO> chargeList = new ArrayList<>();
    private List<ProjectSupplyCreateReqVO> supplyList = new ArrayList<>();
}
