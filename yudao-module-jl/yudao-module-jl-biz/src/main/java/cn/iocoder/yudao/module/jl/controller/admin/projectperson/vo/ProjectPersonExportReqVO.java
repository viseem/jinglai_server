package cn.iocoder.yudao.module.jl.controller.admin.projectperson.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目人员 Excel 导出 Request VO，参数和 ProjectPersonPageReqVO 是一致的")
@Data
public class ProjectPersonExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "人员", example = "7732")
    private Long userId;

    @Schema(description = "类型", example = "1")
    private String type;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "项目id", example = "13810")
    private Long projectId;

}
