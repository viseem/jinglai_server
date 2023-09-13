package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 公司实验物资库存 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CompanySupplyBaseVO {

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotNull(message = "名称不能为空")
    private String name;

    @Schema(description = "规则/单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "规则/单位不能为空")
    private String feeStandard;

    @Schema(description = "单量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "单量不能为空")
    private Integer unitAmount;

    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "数量不能为空")
    private Integer quantity;

    @Schema(description = "物资 id", example = "5243")
    private Long supplyId;

    @Schema(description = "存储位置")
    private String location;

    @Schema(description = "项目物资id", example = "5300")
    private Long projectSupplyId;

    @Schema(description = "所属客户", example = "23534")
    private Long customerId;

    @Schema(description = "所属项目", example = "12689")
    private Long projectId;

    @Schema(description = "所属类型：公司、客户", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "所属类型：公司、客户不能为空")
    private String ownerType;

    @Schema(description = "单价")
    private String unitFee;

    @Schema(description = "有效期")
    private String validDate;

    @Schema(description = "物资快照名称", example = "王五")
    private String fileName;

    @Schema(description = "物资快照地址", example = "https://www.iocoder.cn")
    private String fileUrl;

    private String brand;
    private String productCode;
}
