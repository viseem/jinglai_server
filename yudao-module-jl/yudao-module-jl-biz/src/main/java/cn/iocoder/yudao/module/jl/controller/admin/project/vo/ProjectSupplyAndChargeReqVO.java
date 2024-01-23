package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 项目管理分页 Request VO")
@Data
@ToString(callSuper = true)
public class ProjectSupplyAndChargeReqVO{

    @Schema(description = "项目id", example = "16310")
    private Long projectId;

    @Schema(description = "报价id", example = "8556",requiredMode = Schema.RequiredMode.REQUIRED)
    private Long quotationId;

    @Schema(description = "客户id", example = "8556")
    private Long customerId;

}
