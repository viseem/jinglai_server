package cn.iocoder.yudao.module.jl.controller.admin.subjectgroupmember.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 专题小组成员 Excel 导出 Request VO，参数和 SubjectGroupMemberPageReqVO 是一致的")
@Data
public class SubjectGroupMemberExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "分组id", example = "4881")
    private Long groupId;

    @Schema(description = "用户id", example = "29187")
    private Long userId;

    @Schema(description = "类型", example = "1")
    private String type;

    @Schema(description = "备注")
    private String mark;

}
