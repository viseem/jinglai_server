package cn.iocoder.yudao.module.jl.controller.admin.worktodotagrelation.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 工作任务 TODO 与标签的关联 Excel 导出 Request VO，参数和 WorkTodoTagRelationPageReqVO 是一致的")
@Data
public class WorkTodoTagRelationExportReqVO {

    @Schema(description = "ID", example = "8085")
    private Long id;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "todo id", example = "2824")
    private Long todoId;

    @Schema(description = "tag id", example = "12250")
    private Long tagId;

}
