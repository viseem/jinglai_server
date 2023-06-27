package cn.iocoder.yudao.module.jl.controller.admin.laboratory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 实验室人员 Excel 导出 Request VO，参数和 LaboratoryUserPageReqVO 是一致的")
@Data
public class LaboratoryUserExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "实验室 id", example = "11854")
    private Long labId;

    @Schema(description = "人员 id", example = "14180")
    private Long userId;

    @Schema(description = "备注描述")
    private String mark;

    @Schema(description = "实验室人员等级")
    private String rank;

}
