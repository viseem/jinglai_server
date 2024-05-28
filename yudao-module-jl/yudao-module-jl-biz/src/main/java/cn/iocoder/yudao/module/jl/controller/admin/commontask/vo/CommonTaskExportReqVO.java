package cn.iocoder.yudao.module.jl.controller.admin.commontask.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 通用任务 Excel 导出 Request VO，参数和 CommonTaskPageReqVO 是一致的")
@Data
public class CommonTaskExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "名称", example = "芋艿")
    private String name;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] startDate;

    @Schema(description = "结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] endDate;

    @Schema(description = "负责人", example = "27670")
    private Long userId;

    @Schema(description = "关注人")
    private String focusIds;

    @Schema(description = "项目id", example = "23366")
    private Long projectId;

    @Schema(description = "客户id", example = "21340")
    private Long customerId;

    @Schema(description = "收费项id", example = "13334")
    private Long chargeitemId;

    @Schema(description = "产品id", example = "12983")
    private Long productId;

    @Schema(description = "categoryid", example = "5217")
    private Long projectCategoryId;

    @Schema(description = "报价id", example = "25871")
    private Long quotationId;

    @Schema(description = "项目", example = "李四")
    private String projectName;

    @Schema(description = "客户", example = "李四")
    private String customerName;

    @Schema(description = "收费项", example = "张三")
    private String chargeitemName;

    @Schema(description = "产品", example = "李四")
    private String productName;

    @Schema(description = "实验目录", example = "王五")
    private String projectCategoryName;

    @Schema(description = "类型", example = "1")
    private String type;

    @Schema(description = "紧急程度")
    private String level;

    @Schema(description = "指派人id", example = "10722")
    private Long assignUserId;

    @Schema(description = "指派人", example = "李四")
    private String assignUserName;

    @Schema(description = "实验室id")
    private String labIds;

    @Schema(description = "排序")
    private Integer sort;

}
