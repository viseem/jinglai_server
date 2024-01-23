package cn.iocoder.yudao.module.jl.controller.admin.cellbase.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 细胞数据分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CellBasePageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "名称", example = "张三")
    private String name;

    @Schema(description = "数量")
    private String quantity;

    @Schema(description = "传代次数", example = "27831")
    private String generationCount;

    @Schema(description = "项目id", example = "9921")
    private Integer projectId;

    @Schema(description = "客户id", example = "13645")
    private Integer customerId;

    @Schema(description = "备注")
    private String mark;

}
