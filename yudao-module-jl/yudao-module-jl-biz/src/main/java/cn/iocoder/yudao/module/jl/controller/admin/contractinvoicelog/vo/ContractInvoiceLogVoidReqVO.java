package cn.iocoder.yudao.module.jl.controller.admin.contractinvoicelog.vo;

import cn.iocoder.yudao.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 合同发票记录更新 Request VO")
@Data
@ToString(callSuper = true)
public class ContractInvoiceLogVoidReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24470")
    @NotNull(message = "ID不能为空")
    private Long id;


    /**
     * 申请作废状态
     */
    private String voidApplyStatus = BpmProcessInstanceResultEnum.PROCESS.getResult().toString();

    /**
     * 申请作废备注
     */
    private String voidApplyMark;

    /**
     * 作废审批备注
     */
    private String voidAuditMark;

    /**
     * 作废审批人
     */
    private String voidAuditUser;
}
