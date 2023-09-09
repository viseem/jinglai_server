package cn.iocoder.yudao.module.jl.controller.admin.workduration.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 工时 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class WorkDurationExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("类型")
    private String type;

    @ExcelProperty("时长(分)")
    private Integer duration;

    @ExcelProperty("项目")
    private Long projectId;

    @ExcelProperty("实验名目")
    private Long projectCategoryId;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("审批状态")
    private String auditStatus;

    @ExcelProperty("审批人")
    private Long auditUserId;

    @ExcelProperty("审批说明")
    private String auditMark;

}
