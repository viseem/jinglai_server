package cn.iocoder.yudao.module.jl.controller.admin.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import javax.validation.constraints.*;

/**
 * 产品库 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProductBaseVO {

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "名称不能为空")
    private String name;

    @Schema(description = "英文名称")
    private String nameEn;

    @Schema(description = "简称")
    private String nameShort;

    @Schema(description = "规格")
    private String spec;

    @Schema(description = "分类")
    private Long cateId;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    private String status="1";

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

    @Schema(description = "销售价格", example = "5952")
    private BigDecimal salePrice;

    @Schema(description = "竞品价格", example = "5940")
    private BigDecimal competePrice;

    @Schema(description = "优惠价格", example = "7321")
    private BigDecimal discountPrice;

    @Schema(description = "已售金额")
    private BigDecimal soldAmount;

    @Schema(description = "已售份数", example = "21751")
    private Integer soldCount;

    @Schema(description = "存量", example = "21751")
    private Integer stockCount;

    @Schema(description = "实施主体")
    private String sopName;
    @Schema(description = "实施主体")
    private String principle;
    @Schema(description = "实施主体")
    private String purpose;
    @Schema(description = "实施主体")
    private String preparation;
    @Schema(description = "实施主体")
    private String caution;
    @Schema(description = "实施主体")
    private String supply;
    @Schema(description = "实施主体")
    private String device;
    @Schema(description = "实施主体")
    private String step;
    @Schema(description = "实施主体")
    private String question;

}
