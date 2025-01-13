package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目管理 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProjectExcelVO {
    @ExcelProperty("项目名称")
    private String name;

    @ExcelProperty("订单合同号")
    private String contractSn;

    @ExcelProperty("客户")
    private String customerName;

    @ExcelProperty("满意度")
    private String customerScore;
    @ExcelProperty("签订日期")
    private String signedTime;

    @ExcelProperty("启动日期")
    private String startTime;

    @ExcelProperty("交付日期")
    private String endTime;

    @ExcelProperty("出库日期")
    private String outTime;

    @ExcelProperty("销售员")
    private String salesName;

    @ExcelProperty("售前")
    private String preManagerName;

    @ExcelProperty("售中")
    private String managerName;

    @ExcelProperty("类别")
    private String typeName;

    @ExcelProperty("合同金额")
    private String contractAmount;

    @ExcelProperty("实际结算金额")
    private String projectOutAmount;

    @ExcelProperty("回款金额")
    private String contractReceivedAmount;

    @ExcelProperty("实验报价")
    private String chargeItemSaleAmount;

    @ExcelProperty("实验成本")
    private String chargeItemCost;

    @ExcelProperty("试剂报价")
    private String supplySaleAmount;

    @ExcelProperty("试剂成本")
    private String supplyCost;

    @ExcelProperty("外包报价")
    private String outsourceSaleAmount;

    @ExcelProperty("外包实验成本")
    private String outsourceCost;

    @ExcelProperty("物流成本")
    private String freightCost;

    @ExcelProperty("税")
    private String taxCost;
}
