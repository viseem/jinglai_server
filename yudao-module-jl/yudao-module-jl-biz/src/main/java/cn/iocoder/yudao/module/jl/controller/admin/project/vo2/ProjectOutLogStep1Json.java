package cn.iocoder.yudao.module.jl.controller.admin.project.vo2;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 项目管理 Excel VO
 *
 * @author 惟象科技
 */
@Schema(description = "管理后台 - 皮带 Response VO")
@Data
@ToString(callSuper = true)
public class ProjectOutLogStep1Json {


    @Schema(description ="实际结算金额")
    private String projectOutAmount;

    @Schema(description ="回款金额")
    private String contractReceivedAmount;

    @Schema(description ="实验报价")
    private String chargeItemSaleAmount;

    @Schema(description ="实验成本")
    private String chargeItemCost;

    @Schema(description ="试剂报价")
    private String supplySaleAmount;

    @Schema(description ="试剂成本")
    private String supplyCost;

    @Schema(description ="外包报价")
    private String outsourceSaleAmount;

    @Schema(description ="外包实验成本")
    private String outsourceCost;

    @Schema(description ="物流成本")
    private String freightCost;

    @Schema(description ="税")
    private String taxCost;
}
