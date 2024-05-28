package cn.iocoder.yudao.module.jl.controller.admin.commontask.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 通用任务 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class CommonTaskExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("内容")
    private String content;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("开始时间")
    private LocalDateTime startDate;

    @ExcelProperty("结束时间")
    private LocalDateTime endDate;

    @ExcelProperty("负责人")
    private Long userId;

    @ExcelProperty("关注人")
    private String focusIds;

    @ExcelProperty("项目id")
    private Long projectId;

    @ExcelProperty("客户id")
    private Long customerId;

    @ExcelProperty("收费项id")
    private Long chargeitemId;

    @ExcelProperty("产品id")
    private Long productId;

    @ExcelProperty("categoryid")
    private Long projectCategoryId;

    @ExcelProperty("报价id")
    private Long quotationId;

    @ExcelProperty("项目")
    private String projectName;

    @ExcelProperty("客户")
    private String customerName;

    @ExcelProperty("收费项")
    private String chargeitemName;

    @ExcelProperty("产品")
    private String productName;

    @ExcelProperty("实验目录")
    private String projectCategoryName;

    @ExcelProperty("类型")
    private String type;

    @ExcelProperty("紧急程度")
    private String level;

    @ExcelProperty("指派人id")
    private Long assignUserId;

    @ExcelProperty("指派人")
    private String assignUserName;

    @ExcelProperty("实验室id")
    private String labIds;

    @ExcelProperty("排序")
    private Integer sort;

}
