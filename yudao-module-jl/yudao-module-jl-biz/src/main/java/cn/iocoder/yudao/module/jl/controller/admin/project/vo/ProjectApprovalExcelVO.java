package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目的状态变更记录 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProjectApprovalExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("申请变更的状态：提前开展、项目开展、暂停、中止、退单、提前出库、出库、售后")
    private String stage;

    @ExcelProperty("申请的备注")
    private String stageMark;

    @ExcelProperty("审批人id")
    private Long approvalUserId;

    @ExcelProperty("审批备注")
    private String approvalMark;

    @ExcelProperty("审批状态：等待审批、批准、拒绝")
    private String approvalStage;

    @ExcelProperty("项目的id")
    private Long projectId;

    @ExcelProperty("安排单id")
    private Long scheduleId;

}
