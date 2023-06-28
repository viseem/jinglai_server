package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目实验名目的状态变更审批 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProjectCategoryApprovalExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("申请变更的状态：开始、暂停、数据审批")
    private String stage;

    @ExcelProperty("申请的备注")
    private String stageMark;

    @ExcelProperty("审批人id")
    private Long approvalUserId;

    @ExcelProperty("审批备注")
    private String approvalMark;

    @ExcelProperty("审批状态：等待审批、已审批")
    private String approvalStage;

    @ExcelProperty("项目的实验名目id")
    private Long projectCategoryId;

    @ExcelProperty("安排单id")
    private Long scheduleId;

}
