package cn.iocoder.yudao.module.jl.controller.admin.subjectgroup.vo.subgroupworkstation;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "专题组 - 统计数据 - 基础信息 VO")
@Data
public class SubjectGroupStatsBaseVO {

    @Schema(description = "起止时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "人员id数组", example = "27395")
    private Long[] userIds;

    @Schema(description = "专题组id", example = "27395")
    private Long subjectGroupId;

}
