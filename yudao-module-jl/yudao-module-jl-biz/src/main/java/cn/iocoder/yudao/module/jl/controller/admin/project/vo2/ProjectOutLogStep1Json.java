package cn.iocoder.yudao.module.jl.controller.admin.project.vo2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

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

    @Schema(description ="合同金额（纸质）")
    private String contractPaperAmount;

    @Schema(description ="合同应收金额（默认结算）")
    private String contractAmount;

    @Schema(description ="毛利润")
    private String projectOutProfit;

    @Schema(description ="毛利润率")
    private String projectOutProfitRate;

    @Schema(description ="原始出库金额")
    private String projectOutOriginAmount;

    @Schema(description ="备注")
    private String outMark;

    @Schema(description ="附件列表")
    private List<AttachmentItem> attachmentList;

    @Data
    public static class AttachmentItem {
        private String fileName;
        private String fileUrl;
    }
}
