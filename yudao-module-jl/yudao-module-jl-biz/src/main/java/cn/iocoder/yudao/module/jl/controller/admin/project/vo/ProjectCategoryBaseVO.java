package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.entity.project.ProjectOnly;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryApproval;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.*;

/**
 * 项目的实验名目 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectCategoryBaseVO {

    @Schema(description = "报价 id", example = "20286")
    private Long quoteId;

    @Schema(description = "项目id", example = "1")
    private Long projectId;

//    private ProjectOnly project;

    @Schema(description = "安排单 id", example = "14245")
    private Long scheduleId;

    @Schema(description = "类型，报价/安排单", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    @NotNull(message = "类型，报价/安排单不能为空")
    private String type = "schedule";

    @Schema(description = "所属实验室id，动物/细胞/分子等", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "所属实验室不能为空")
    private Long labId;

    @Schema(description = "名目的实验类型，动物/细胞/分子等", example = "2")
    private String categoryType;

    @Schema(description = "实验名目库的名目 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17935")
    @NotNull(message = "实验名目库的名目 id不能为空")
    private Long categoryId;

    @Schema(description = "实验人员", example = "17520")
    private Long operatorId;

    private User operator;

    @Schema(description = "客户需求")
    private String demand;

    @Schema(description = "截止日期")
    private String deadline;

    @Schema(description = "干扰项")
    private String interference;

    @Schema(description = "依赖项(json数组多个)")
    private String dependIds;

    @Schema(description = "实验名目名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotNull(message = "实验名目名字不能为空")
    private String name;

    @Schema(description = "备注")
    private String mark;

    /**
     * 当前实验的状态，未开展、开展中、数据审核、已完成
     */
    @Schema(description = "当前实验的状态")
    private String stage;

    /**
     * 原始数据富文本
     */
    @Schema(description = "原始数据")
    private String rawData;

}
