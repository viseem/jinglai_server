package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目物资变更日志分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SupplyLogPageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "相关的id，采购、寄来、取货", example = "2967")
    private Long refId;

    @Schema(description = "类型：采购、寄来、取货", example = "2")
    private Long type;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "备注描述")
    private String mark;

    @Schema(description = "变更描述")
    private String log;

}
