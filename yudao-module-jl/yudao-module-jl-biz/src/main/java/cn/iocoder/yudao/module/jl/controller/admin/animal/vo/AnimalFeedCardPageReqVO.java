package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 动物饲养鼠牌分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AnimalFeedCardPageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "饲养单id", example = "8438")
    private Long feedOrderId;

    @Schema(description = "序号")
    private Integer seq;

    @Schema(description = "品系品种")
    private String breed;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "性别")
    private Byte gender;

    @Schema(description = "数量")
    private Integer quantity;

    @Schema(description = "项目id", example = "3741")
    private Long projectId;

    @Schema(description = "客户id", example = "6508")
    private Long customerId;

}
