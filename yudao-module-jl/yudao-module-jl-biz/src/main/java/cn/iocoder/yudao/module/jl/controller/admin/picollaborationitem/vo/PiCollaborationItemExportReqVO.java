package cn.iocoder.yudao.module.jl.controller.admin.picollaborationitem.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - pi组协作明细 Excel 导出 Request VO，参数和 PiCollaborationItemPageReqVO 是一致的")
@Data
public class PiCollaborationItemExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "协助的主表id", example = "8954")
    private Long collaborationId;

    @Schema(description = "pi组id", example = "6278")
    private Long piId;

    @Schema(description = "占比")
    private BigDecimal percent;

    @Schema(description = "备注")
    private String mark;

}
