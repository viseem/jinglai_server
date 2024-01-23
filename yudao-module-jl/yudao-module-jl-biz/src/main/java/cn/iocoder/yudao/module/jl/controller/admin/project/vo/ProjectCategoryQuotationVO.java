package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.entity.user.User;
import cn.iocoder.yudao.module.jl.enums.ProjectCategoryStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 项目的实验名目 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectCategoryQuotationVO {

    private Boolean isOld=false;
    @Schema(description = "originId", example = "20286")
    private Long originId;
    @Schema(description = "id", example = "20286")
    private Long id;
    @Schema(description = "报价 id", example = "20286")
    private Long quoteId;

    @Schema(description = "项目id", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long projectId;

    @Schema(description = "报价id", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long quotationId;

    @Schema(description = "实验的labIds", example = "1")
    private String labIds;

    @Schema(description = "参与者", example = "1")
    private String focusIds;

    @Schema(description = "安排单 id", example = "14245")
    private Long scheduleId;

    @Schema(description = "类型，报价/安排单", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    @NotNull(message = "类型，报价/安排单不能为空")
    private String type = "schedule";

    @Schema(description = "所属实验室id，动物/细胞/分子等")
    private Long labId;

    @Schema(description = "名目的实验类型，动物/细胞/分子等", example = "2")
    private String categoryType;

    @Schema(description = "实验名目库的名目 id")
    private Long categoryId;

    @Schema(description = "实验负责人", example = "17520")
    private Long operatorId;

    @Schema(description = "实验员", example = "17520")
    private String operatorIds;

    private User operator;

    @Schema(description = "客户需求")
    private String demand;

    @Schema(description = "开始日期")
    private String startDate;

    @Schema(description = "截止日期")
    private String deadline;

    @Schema(description = "干扰项")
    private String interference;

    @Schema(description = "依赖项(json数组多个)")
    private String dependIds;

    @Schema(description = "实验名目名字")
    private String name;

    @Schema(description = "原始内容")
    private String content;

    @Schema(description = "备注")
    private String mark;

    /**
     * 当前实验的状态，未开展、开展中、数据审核、已完成
     */
    @Schema(description = "当前实验的状态")
    private String stage = ProjectCategoryStatusEnums.WAIT_DO.getStatus();

    /**
     * 原始数据富文本
     */
    @Schema(description = "原始数据")
    private String rawData;

}
