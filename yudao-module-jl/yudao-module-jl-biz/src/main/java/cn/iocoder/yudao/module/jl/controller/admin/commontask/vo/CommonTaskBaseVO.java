package cn.iocoder.yudao.module.jl.controller.admin.commontask.vo;

import cn.iocoder.yudao.module.jl.entity.commontask.CommonTask;
import cn.iocoder.yudao.module.jl.entity.product.ProductSelector;
import cn.iocoder.yudao.module.jl.entity.project.ProjectChargeitem;
import cn.iocoder.yudao.module.jl.enums.CommonTaskCreateTypeEnums;
import cn.iocoder.yudao.module.jl.enums.CommonTaskStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 通用任务 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CommonTaskBaseVO {

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotNull(message = "名称不能为空")
    private String name;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "状态")
    private Integer status = CommonTaskStatusEnums.WAIT_SEND.getStatus();

    @Schema(description = "开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startDate;

    @Schema(description = "结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime endDate;

    @Schema(description = "负责人", requiredMode = Schema.RequiredMode.REQUIRED, example = "27670")
    @NotNull(message = "负责人不能为空")
    private Long userId;

    @Schema(description = "负责人昵称")
    private String userNickname;

    @Schema(description = "关注人")
    private String focusIds;

    @Schema(description = "实验员")
    private String experIds;

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

    @Schema(description = "类型")
    private Integer type;

    @Schema(description = "创建类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "创建类型不能为空")
    // 默认管理任务
    private Integer createType = CommonTaskCreateTypeEnums.MANAGE.getStatus();

    @Schema(description = "紧急程度")
    private Integer level;

    @Schema(description = "指派人id")
    private Long assignUserId;

    @Schema(description = "指派人")
    private String assignUserName;

    @Schema(description = "实验室id")
    private String labIds;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "父级id")
    private Long parentId=0L;

    private List<ProductSelector> productList;

    // 创建的时候用
    private List<ProjectChargeitem> chargeList;

    private CommonTask parentTask;
}
