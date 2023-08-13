package cn.iocoder.yudao.module.jl.controller.admin.contract.vo;

import cn.iocoder.yudao.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 合同状态变更记录更新 Request VO")
@Data
@ToString(callSuper = true)
public class ContractApprovalUpdateReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12170")
    @NotNull(message = "ID不能为空")
    private Long id;


    @Schema(description = "审批状态",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "审批状态不能为空")
    private String approvalStage;

}
