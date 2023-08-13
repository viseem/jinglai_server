package cn.iocoder.yudao.module.jl.controller.admin.approval.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 审批 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ApprovalExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("内容")
    private String content;

    @ExcelProperty("内容详情id")
    private Long refId;

    @ExcelProperty("类型")
    private String type;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("当前审批人")
    private String currentApprovalUser;

    @ExcelProperty("当前审批状态")
    private String currentApprovalStage;

}
