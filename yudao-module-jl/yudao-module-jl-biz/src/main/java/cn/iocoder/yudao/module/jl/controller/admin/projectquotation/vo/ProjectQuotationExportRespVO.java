package cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "管理后台 - 项目报价 Response VO")
@Data
@ToString(callSuper = true)
public class ProjectQuotationExportRespVO  {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4075")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "版本号")
    private String code;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "项目id", requiredMode = Schema.RequiredMode.REQUIRED, example = "19220")
    @NotNull(message = "项目id不能为空")
    private Long projectId;

    @Schema(description = "客户id")
    private Long customerId;

    @Schema(description = "supplycount")
    private Integer supplyCount;

    @Schema(description = "chargeCount")
    private Integer chargeCount;

    @Schema(description = "rowCount")
    private Integer rowCount;

    @Schema(description = "quotationItems")
    private List<ProjectQuotationItemVO> quotationItems = new ArrayList<>();

}
