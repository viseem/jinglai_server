package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.entity.laboratory.LaboratoryLab;
import cn.iocoder.yudao.module.jl.entity.project.ProjectChargeitem;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSop;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSupply;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryApproval;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryAttachment;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryLog;
import cn.iocoder.yudao.module.jl.entity.taskrelation.TaskRelationOnly;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 项目的实验名目 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectCategoryRespVO extends ProjectCategoryBaseVO {

    @Schema(description = "岗位ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11767")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    private List<ProjectChargeitem> chargeList;
    private List<ProjectSupply> supplyList;

    private List<ProjectSop> sopList;

    private List<ProjectCategoryAttachment> attachmentList;

//    private ProjectCategoryApproval approval;

    private List<ProjectCategoryApproval> approvalList;

    private ProjectCategoryApproval latestApproval;

    //审批的状态 通过 还是未通过
    private String approvalStage;
    //申请的变更的状态
    private String requestStage;
    private ProjectSimple project;

    //实验记录
    private List<ProjectCategoryLog> logs;

    private LaboratoryLab lab;

    private  List<TaskRelationOnly> relations;
}
