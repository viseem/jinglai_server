package cn.iocoder.yudao.module.jl.controller.admin.devicecate.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备分类分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeviceCatePageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "名称", example = "赵六")
    private String name;

    @Schema(description = "说明")
    private String mark;

    @Schema(description = "父级id", example = "4842")
    private Long parentId;

    @Schema(description = "标签")
    private String tagIds;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "详细介绍")
    private String content;

    private Boolean withChild=false;
}
