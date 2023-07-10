package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.ProjectCategoryAttachmentBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "管理后台 - 项目报价保存 Request VO")
@Data
@ToString(callSuper = true)
public class ProjectScheduleCategorySaveReqVO extends ProjectCategoryBaseVO {
    @Schema(description = "安排单 id", example = "1", nullable = true, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long id;

    @Schema(description = "收费项", example = "[]")
    private List<ProjectChargeitemSubClass> chargeList;

    @Schema(description = "物资项", example = "[]")
    private List<ProjectSupplySubClass> supplyList;

    @Schema(description = "SOP步骤", example = "[]")
    private List<ProjectSopBaseVO> sopList;

    @Schema(description = "需求要求附件", example = "[]")
    private List<ProjectCategoryAttachmentBaseVO> attachmentList;
}
