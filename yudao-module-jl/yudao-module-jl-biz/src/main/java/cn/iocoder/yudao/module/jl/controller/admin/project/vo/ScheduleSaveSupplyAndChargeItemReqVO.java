package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.entity.project.ProjectChargeitem;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSupply;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "管理后台 - 项目报价保存 Request VO")
@Data
@ToString(callSuper = true)
public class ScheduleSaveSupplyAndChargeItemReqVO {
    @Schema(description = "收费项", example = "[]")
    private List<ProjectChargeitem> chargeList;

    @Schema(description = "物资项", example = "[]")
    private List<ProjectSupply> supplyList;

}