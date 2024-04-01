package cn.iocoder.yudao.module.jl.controller.admin.product.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 产品库分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductPageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "名称", example = "王五")
    private String name;

    @Schema(description = "分类")
    private Long cateId;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "pi组", example = "7318")
    private Long piGroupId;

    @Schema(description = "实验负责人", example = "12399")
    private Long experId;

    @Schema(description = "信息负责人", example = "2931")
    private Long infoUserId;

    @Schema(description = "实施主体")
    private String subject;

    @Schema(description = "供应商id", example = "17063")
    private Long supplierId;

    @Schema(description = "标准价格", example = "31388")
    private BigDecimal standardPrice;

    @Schema(description = "成本价格", example = "5952")
    private BigDecimal costPrice;

    @Schema(description = "竞品价格", example = "5940")
    private BigDecimal competePrice;

    @Schema(description = "优惠价格", example = "7321")
    private BigDecimal discountPrice;

    @Schema(description = "已售金额")
    private BigDecimal soldAmount;

    @Schema(description = "已售份数", example = "21751")
    private Integer soldCount;

}
