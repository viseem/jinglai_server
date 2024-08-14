package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.entity.laboratory.Category;
import cn.iocoder.yudao.module.jl.entity.project.ProcurementItem;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategory;
import cn.iocoder.yudao.module.jl.enums.ProjectQuotationItemCreateTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.validation.constraints.*;

/**
 * 项目中的实验名目的物资项 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectSupplyBaseVO {

    @Schema(description = "创建的类型")
    private Integer createType= ProjectQuotationItemCreateTypeEnums.QUOTATION.getStatus();

    @Schema(description = "选中的实验名目 id")
    private Long projectCategoryId;

    @Schema(description = "原始的实验名目 id")
    private Long categoryId;


    @Schema(description = "物资 id")
    private Long supplyId;

    private Long projectId;

    private Long scheduleId;
    private Long quotationId;
    private Integer sort;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotNull(message = "名称不能为空")
    private String name;

    @Schema(description = "规则/单位")
    private String feeStandard;

    /**
     * 规格
     */
    @Schema(description = "规格")
    private String spec;

    /**
     * 单位
     */
    private String unit;

    /**
     * 采购时的规格，这个可能会变化，每次采购时候会更新这个
     */
    @Schema(description = "采购规格")
    private String procurementSpec;

    // 这个是报价时候的单价，也是最初的销售单价
    @Schema(description = "单价", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "单价不能为空")
    private Integer unitFee;

    // 这个是销售单价，每次采购的时候会更新 一下这个值
    @Schema(description = "销售单价")
    private Integer salePrice;

    private Integer officialPrice;

    @Schema(description = "单量")
    private Integer unitAmount;

    @Schema(description = "成本价")
    private Integer buyPrice;

    @Schema(description = "原单价")
    private Integer originPrice;

    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "数量不能为空")
    private Integer quantity;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "内部备注")
    private String internalMark;

    private String brand;
    private String catalogNumber;

    private String productCode;

    @Schema(description = "收货地址")
    private String receiveRoomName;

    private String type;

    @Schema(description = "物品来源")
    private String source;

    @Schema(description = "采购总量")
    private Integer procurementQuantity;

    @Schema(description = "库存总量")
    private Integer inventoryQuantity;

    private Integer finalUsageNum;

    private Integer isAppend;

    /**
     * 当前单价
     */
    private BigDecimal currentPrice;

    /**
     * 当前数量
     */
    private Integer currentQuantity;

    /**
     * 当前规格
     */
    private String currentSpec;

    /**
     * 当前品牌
     */
    private String currentBrand;

    /**
     * 当前目录号
     */
    private String currentCatalogNumber;

    private Boolean deletedStatus=false;


}
