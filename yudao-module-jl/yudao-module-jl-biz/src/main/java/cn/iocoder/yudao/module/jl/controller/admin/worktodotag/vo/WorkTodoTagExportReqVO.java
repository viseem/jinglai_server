package cn.iocoder.yudao.module.jl.controller.admin.worktodotag.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 工作任务 TODO 的标签 Excel 导出 Request VO，参数和 WorkTodoTagPageReqVO 是一致的")
@Data
public class WorkTodoTagExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "名字", example = "王五")
    private String name;

    @Schema(description = "类型：系统通用/用户自己创建", example = "2")
    private String type;

}
