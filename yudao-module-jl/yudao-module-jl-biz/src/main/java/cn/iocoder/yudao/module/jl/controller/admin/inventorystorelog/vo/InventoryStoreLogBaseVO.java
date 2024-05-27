package cn.iocoder.yudao.module.jl.controller.admin.inventorystorelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * 物品出入库日志 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class InventoryStoreLogBaseVO {

    @Schema(description = "物品来源")
    private String source;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "目录号")
    private String catalogNumber;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "规格")
    private String spec;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "变更类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "变更类型不能为空")
    private String changeType;

    @Schema(description = "变更数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "变更数量不能为空")
    private BigDecimal changeNum;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "房间id", example = "14616")
    private Long roomId;

    @Schema(description = "容器id", example = "3090")
    private Long containerId;

    @Schema(description = "位置id", example = "4816")
    private Long placeId;

    @Schema(description = "位置详情")
    private String location;

    @Schema(description = "明细的id", requiredMode = Schema.RequiredMode.REQUIRED, example = "16824")
    @NotNull(message = "明细的id不能为空")
    private Long sourceItemId;

    @Schema(description = "项目物资的id", example = "18751")
    private Long projectSupplyId;

    @Schema(description = "项目id", example = "20536")
    private Long projectId;

    @Schema(description = "客户id", example = "20536")
    private Long customerId;

    @Schema(description = "购销合同id", example = "28993")
    private Long purchaseContractId;

    @Schema(description = "采购单id", example = "10104")
    private Long procurementId;

    @Schema(description = "操作时间", example = "10104")
    private LocalDateTime operateTime;

    @Schema(description = "操作者id", example = "10104")
    private Long operatorId = getLoginUserId();

}
