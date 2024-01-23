package cn.iocoder.yudao.module.jl.controller.admin.subjectgroup.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 专题小组 Excel 导出 Request VO，参数和 SubjectGroupPageReqVO 是一致的")
@Data
public class SubjectGroupExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "名称", example = "赵六")
    private String name;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "专题")
    private String area;

    @Schema(description = "领域")
    private String subject;

    @Schema(description = "组长", example = "27395")
    private Long leaderId;

    @Schema(description = "商户组长", example = "16307")
    private Long businessLeaderId;

    @Schema(description = "编号")
    private String code;

}
