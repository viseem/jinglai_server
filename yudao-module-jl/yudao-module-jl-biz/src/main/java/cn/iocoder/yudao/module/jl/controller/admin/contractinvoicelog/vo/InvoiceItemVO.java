package cn.iocoder.yudao.module.jl.controller.admin.contractinvoicelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "管理后台 - 项目报价 Response VO")
@Data
@ToString(callSuper = true)
public class InvoiceItemVO {

    @Schema(description = "名称")
    private String name;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "货号")
    private String productCode;

    @Schema(description = "规格")
    private String spec;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "单价(元)")
    private String price;

    @Schema(description = "数量")
    private String quantity;

    @Schema(description = "金额")
    private String amount;

}
