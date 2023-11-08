package cn.iocoder.yudao.module.jl.controller.admin.projectsupplierinvoice.vo;

import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 采购供应商发票 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectSupplierInvoiceRespVO extends ProjectSupplierInvoiceBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3438")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    private ProjectSimple project;

}
