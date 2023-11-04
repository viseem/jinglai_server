package cn.iocoder.yudao.module.jl.controller.admin.projectquotation;

import cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo.ProjectQuotationBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 项目报价更新 Request VO")
@Data
@ToString(callSuper = true)
public class ProjectQuotationUpdatePlanReqVO {

    @Schema(description = "ID")
    private Long id;


    @Schema(description = "方案", requiredMode = Schema.RequiredMode.REQUIRED, example = "4075")
    private String planText;


    @Schema(description = "版本号")
    private String code;

    @Schema(description = "备注")
    private String mark;
    @Schema(description = "项目id不能为空", requiredMode = Schema.RequiredMode.REQUIRED, example = "4075")
    private Long projectId;

    private Long customerId;


}
