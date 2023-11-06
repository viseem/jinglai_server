package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目的实验名目分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectCategoryPageReqVO extends PageParam {

    private String stage;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "项目 id", example = "14245")
    private Long projectId;
    @Schema(description = "安排单 id", example = "14245")
    private Long scheduleId;
    @Schema(description = "报价id", example = "14245")
    private Long quotationId;

    @Schema(description = "类型，报价/安排单", example = "1")
    private String type = "schedule";

    private List<String> types;

    @Schema(description = "名目的实验类型，动物/细胞/分子等", example = "2")
    private String categoryType;

    @Schema(description = "实验名目库的名目 id", example = "17935")
    private Long categoryId;

    @Schema(description = "实验人员", example = "17520")
    private Long operatorId;

    @Schema(description = "客户需求")
    private String demand;

    @Schema(description = "截止日期")
    private String deadline;

    @Schema(description = "干扰项")
    private String interference;

    @Schema(description = "依赖项(json数组多个)")
    private String dependIds;

    @Schema(description = "实验名目名字", example = "赵六")
    private String name;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "实验室id", example = "17935")
    private Long labId;

    @Schema(description = "参与者id", example = "17935")
    private Long focusId;

    @Schema(description = "是否有反馈", example = "17935")
    private Byte hasFeedback;

    private String approvalStage;
    private String requestStage;

}
