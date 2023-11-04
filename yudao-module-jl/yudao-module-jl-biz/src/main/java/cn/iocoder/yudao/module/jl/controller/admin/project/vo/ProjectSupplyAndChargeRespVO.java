package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.entity.project.ProjectChargeitem;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSupply;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - 项目管理 Response VO")
@Data
@ToString(callSuper = true)
public class ProjectSupplyAndChargeRespVO {
    private List<ProjectSupply> supplyList;
    private List<ProjectChargeitem> chargeList;
}
