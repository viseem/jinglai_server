package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.*;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 项目管理 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectBaseVO {

    @Schema(description = "当前安排单 id", example = "15320")
    private Long currentScheduleId;

    private Long subjectGroupId;

    private Long currentQuotationId;

    private String processInstanceId;
    private String outboundApplyResult;

    private Long outboundUserId;

    @Schema(description = "销售线索 id", example = "15320")
    private Long salesleadId;

    @Schema(description = "项目名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotNull(message = "项目名字不能为空")
    private String name;

    @Schema(description = "编号")
    private String code;

    @Schema(description = "项目开展阶段")
    private String stage;

    @Schema(description = "项目状态", example = "1")
    private String status;

    @Schema(description = "项目类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "项目类型不能为空")
    private String type;

    @Schema(description = "启动时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startDate;

    @Schema(description = "截止时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime endDate;

    @Schema(description = "项目负责人", example = "6150")
    private Long managerId;

    @Schema(description = "项目售前负责人", example = "6150")
    private Long preManagerId;

    @Schema(description = "项目售后负责人", example = "6150")
    private Long afterManagerId;

    @Schema(description = "实验负责人", example = "6150")
    private Long experId;

/*    @Schema(description = "参与者 ids，数组")
    private String participants;*/

    @Schema(description = "参与者 ids，数组")
    private String focusIds;

    @Schema(description = "销售 id", example = "16310")
    private Long salesId;

    @Schema(description = "销售 id", example = "8556")
    private Long customerId;

}
