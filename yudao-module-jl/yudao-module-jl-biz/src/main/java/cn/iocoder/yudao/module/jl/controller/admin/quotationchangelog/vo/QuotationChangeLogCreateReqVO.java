package cn.iocoder.yudao.module.jl.controller.admin.quotationchangelog.vo;

import cn.iocoder.yudao.module.jl.entity.project.ProjectCategoryOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectChargeitem;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSupplyOnly;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 报价变更日志创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QuotationChangeLogCreateReqVO extends QuotationChangeLogBaseVO {

    @Schema(description = "实验材料", requiredMode = Schema.RequiredMode.REQUIRED, example = "23423")
    @NotNull(message = "实验材料不能为空")
    private List<ProjectSupplyOnly> supplyList;
    @Schema(description = "实验类目", requiredMode = Schema.RequiredMode.REQUIRED, example = "23423")
    @NotNull(message = "实验类目不能为空")
    private List<ProjectCategoryOnly> categoryList;
    @Schema(description = "收费项", requiredMode = Schema.RequiredMode.REQUIRED, example = "23423")
    @NotNull(message = "收费项不能为空")
    private List<ProjectChargeitem> chargeList;

}
