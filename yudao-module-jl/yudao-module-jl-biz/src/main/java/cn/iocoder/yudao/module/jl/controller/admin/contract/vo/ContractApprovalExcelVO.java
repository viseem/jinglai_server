package cn.iocoder.yudao.module.jl.controller.admin.contract.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 合同状态变更记录 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ContractApprovalExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("申请变更的状态")
    private String stage;

    @ExcelProperty("申请的备注")
    private String stageMark;

    @ExcelProperty("审批备注")
    private String approvalMark;

    @ExcelProperty("审批状态：等待审批、批准、拒绝")
    private Byte approvalStage;

    @ExcelProperty("合同id")
    private Long contractId;

    @ExcelProperty("变更前状态")
    private String originStage;

    @ExcelProperty("流程实例的编号")
    private String processInstanceId;

}
