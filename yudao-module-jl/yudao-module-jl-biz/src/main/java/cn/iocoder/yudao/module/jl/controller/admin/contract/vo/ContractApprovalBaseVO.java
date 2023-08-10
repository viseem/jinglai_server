package cn.iocoder.yudao.module.jl.controller.admin.contract.vo;

import cn.iocoder.yudao.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 合同状态变更记录 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ContractApprovalBaseVO {

    @Schema(description = "申请变更的状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "申请变更的状态不能为空")
    private String stage;

    @Schema(description = "申请的备注", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "申请的备注不能为空")
    private String stageMark;

    @Schema(description = "审批备注")
    private String approvalMark;

    @Schema(description = "审批状态：等待审批、批准、拒绝")
    private String approvalStage = BpmProcessInstanceResultEnum.PROCESS.getResult().toString();

    @Schema(description = "合同id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7505")
    @NotNull(message = "合同id不能为空")
    private Long contractId;

    @Schema(description = "变更前状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "变更前状态不能为空")
    private String originStage;

    @Schema(description = "流程实例的编号", example = "811")
    private String processInstanceId;

    @Schema(description = "合同结算金额", example = "811", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "结算金额不能为空")
    private Integer realPrice;

    @Schema(description = "变更前结算金额", example = "811")
    private Integer originRealPrice;

}
