package cn.iocoder.yudao.module.jl.controller.admin.projectoutlog.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - example 空分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectOutLogPageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "项目id", example = "6093")
    private Long projectId;

    @Schema(description = "客户id", example = "22417")
    private Long customerId;

    @Schema(description = "内部数据评审json")
    private String dataSignJson;

    @Schema(description = "客户评价")
    private String customerComment;

    @Schema(description = "客户打分")
    private String customerScoreJson;

    @Schema(description = "报价金额", example = "10360")
    private BigDecimal quotationPrice;

    @Schema(description = "报价备注")
    private String quotationMark;

    @Schema(description = "已收金额", example = "14734")
    private BigDecimal receivedPrice;

    @Schema(description = "已收备注")
    private String receivedMark;

    @Schema(description = "合同金额", example = "11191")
    private BigDecimal contractPrice;

    @Schema(description = "合同备注")
    private String contractMark;

    @Schema(description = "材料成本", example = "11496")
    private BigDecimal supplyPrice;

    @Schema(description = "材料备注")
    private String supplyMark;

    @Schema(description = "委外成本", example = "2216")
    private BigDecimal outsourcePrice;

    @Schema(description = "委外备注")
    private String outsourceMark;

    @Schema(description = "客户签字链接", example = "https://www.iocoder.cn")
    private String customerSignImgUrl;

    @Schema(description = "客户签字时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] custoamerSignTime;

    @Schema(description = "步骤的确认日志")
    private String stepsJsonLog;

    @Schema(description = "出库金额，理论上等于已收金额", example = "12597")
    private BigDecimal resultPrice;

    @Schema(description = "出库金额备注")
    private String resultMark;

}
