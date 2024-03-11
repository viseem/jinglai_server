package cn.iocoder.yudao.module.jl.controller.admin.commonoperatelog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 通用操作记录 Excel 导出 Request VO，参数和 CommonOperateLogPageReqVO 是一致的")
@Data
public class CommonOperateLogExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "旧内容")
    private String oldContent;

    @Schema(description = "新内容")
    private String newContent;

    @Schema(description = "父类型", example = "2")
    private String type;

    @Schema(description = "子类型", example = "2")
    private String subType;

    @Schema(description = "事件类型", example = "2")
    private String eventType;

    @Schema(description = "父类型关联id", example = "22355")
    private Long refId;

    @Schema(description = "子类型关联id", example = "29358")
    private Long subRefId;

}
