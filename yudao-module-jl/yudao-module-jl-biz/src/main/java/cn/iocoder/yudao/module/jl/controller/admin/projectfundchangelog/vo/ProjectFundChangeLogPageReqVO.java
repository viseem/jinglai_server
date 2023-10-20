package cn.iocoder.yudao.module.jl.controller.admin.projectfundchangelog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 款项计划变更日志分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectFundChangeLogPageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "款项计划", example = "6523")
    private Long projectFundId;

    @Schema(description = "原状态", example = "1")
    private String originStatus;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "销售id", example = "693")
    private Long salesId;

    @Schema(description = "客户id", example = "172")
    private Long customerId;

    @Schema(description = "项目id", example = "24100")
    private Long projectId;

    @Schema(description = "合同id", example = "18622")
    private Long contractId;

    @Schema(description = "款项名称", example = "李四")
    private String name;

    @Schema(description = "款项金额", example = "29033")
    private Long price;

    @Schema(description = "款项应收日期")
    private LocalDateTime deadline;

    @Schema(description = "变更类型 默认1：状态变更", example = "1")
    private String changeType;

}
