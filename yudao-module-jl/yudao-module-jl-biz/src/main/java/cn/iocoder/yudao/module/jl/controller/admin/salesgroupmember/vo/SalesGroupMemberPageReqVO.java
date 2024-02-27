package cn.iocoder.yudao.module.jl.controller.admin.salesgroupmember.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 销售分组成员分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SalesGroupMemberPageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "分组id", example = "20497")
    private Integer groupId;

    @Schema(description = "用户id", example = "13658")
    private Integer userId;

    @Schema(description = "月度回款目标")
    private BigDecimal monthRefundKpi;

    @Schema(description = "月度订单目标")
    private BigDecimal monthOrderKpi;

    @Schema(description = "备注")
    private String mark;

}
