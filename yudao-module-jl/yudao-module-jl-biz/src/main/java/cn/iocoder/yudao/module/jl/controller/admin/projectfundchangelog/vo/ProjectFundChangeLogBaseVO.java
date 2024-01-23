package cn.iocoder.yudao.module.jl.controller.admin.projectfundchangelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 款项计划变更日志 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectFundChangeLogBaseVO {

    @Schema(description = "款项计划", requiredMode = Schema.RequiredMode.REQUIRED, example = "6523")
    @NotNull(message = "款项计划不能为空")
    private Long projectFundId;

    @Schema(description = "原状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "原状态不能为空")
    private String originStatus;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    private String status;

    @Schema(description = "备注", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "备注不能为空")
    private String mark;

    @Schema(description = "销售id", requiredMode = Schema.RequiredMode.REQUIRED, example = "693")
    @NotNull(message = "销售id不能为空")
    private Long salesId;

    @Schema(description = "客户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "172")
    @NotNull(message = "客户id不能为空")
    private Long customerId;

    @Schema(description = "项目id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24100")
    @NotNull(message = "项目id不能为空")
    private Long projectId;

    @Schema(description = "合同id", requiredMode = Schema.RequiredMode.REQUIRED, example = "18622")
    @NotNull(message = "合同id不能为空")
    private Long contractId;

    @Schema(description = "款项名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotNull(message = "款项名称不能为空")
    private String name;

    @Schema(description = "款项金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "29033")
    @NotNull(message = "款项金额不能为空")
    private Long price;

    @Schema(description = "款项应收日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime deadline;

    @Schema(description = "变更类型 默认1：状态变更", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "变更类型 默认1：状态变更不能为空")
    private String changeType;

}
