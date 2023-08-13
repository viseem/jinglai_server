package cn.iocoder.yudao.module.jl.controller.admin.approval.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 审批流程 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ApprovalProgressExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("审批id")
    private Long approvalId;

    @ExcelProperty("用户id")
    private Integer toUserId;

    @ExcelProperty("审批状态")
    private String approvalStage;

    @ExcelProperty("审批备注")
    private String approvalMark;

    @ExcelProperty("审批类型：抄送，审批")
    private String type;

}
