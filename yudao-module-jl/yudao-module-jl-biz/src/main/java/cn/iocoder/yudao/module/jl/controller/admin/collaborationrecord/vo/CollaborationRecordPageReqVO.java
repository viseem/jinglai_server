package cn.iocoder.yudao.module.jl.controller.admin.collaborationrecord.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 通用协作记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CollaborationRecordPageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "外键id", example = "11122")
    private Long refId;

    @Schema(description = "类型", example = "1")
    private String type;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "父级id", example = "13368")
    private Long pid;

    @Schema(description = "状态", example = "2")
    private String status;

}