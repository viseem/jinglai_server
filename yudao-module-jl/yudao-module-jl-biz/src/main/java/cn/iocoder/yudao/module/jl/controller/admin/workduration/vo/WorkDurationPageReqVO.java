package cn.iocoder.yudao.module.jl.controller.admin.workduration.vo;

import cn.iocoder.yudao.module.jl.enums.DataAttributeTypeEnums;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 工时分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkDurationPageReqVO extends PageParam {

    private String attribute = DataAttributeTypeEnums.MY.getStatus();

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "类型", example = "1")
    private String type;

    @Schema(description = "时长(分)")
    private Integer duration;

    @Schema(description = "项目", example = "31128")
    private Long projectId;

    @Schema(description = "实验名目", example = "32707")
    private Long projectCategoryId;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "审批状态", example = "1")
    private String auditStatus;

    @Schema(description = "审批人", example = "1117")
    private Long auditUserId;

    @Schema(description = "审批说明")
    private String auditMark;

}
