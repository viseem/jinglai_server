package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import cn.iocoder.yudao.module.jl.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 项目的状态变更记录 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectApprovalRespVO extends ProjectApprovalBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25843")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "申请人")
    private User user;


    @Schema(description = "审批人id", example = "378")
    private Long approvalUserId;

    @Schema(description = "审批备注")
    private String approvalMark;

    @Schema(description = "审批状态：等待审批、批准、拒绝")
    private String approvalStage;

    private ProjectSimple project;


    private String processInstanceId;
}
