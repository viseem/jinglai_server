package cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo;

import cn.iocoder.yudao.module.jl.entity.project.ProjectChargeitem;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSupply;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "管理后台 - 项目报价更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectQuotationSaveReqVO extends ProjectQuotationBaseVO {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "收费项", example = "[]")
    private List<ProjectChargeitem> chargeList;

    @Schema(description = "物资项", example = "[]")
    private List<ProjectSupply> supplyList;
}
