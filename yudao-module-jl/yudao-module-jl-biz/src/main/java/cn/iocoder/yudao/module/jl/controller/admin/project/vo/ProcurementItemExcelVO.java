package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目采购单申请明细 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProcurementItemExcelVO {

    @ExcelProperty("申请人")
    private String applierName;

    @ExcelProperty("申请日期")
    private String createTime;

    @ExcelProperty("批准日期")
    private String purchaseAcceptTime;


    @ExcelProperty("品牌")
    private String brand;


    @ExcelProperty("货号")
    private String catalogNumber;


    @ExcelProperty("明细名称")
    private String name;


    @ExcelProperty("规格")
    private String spec;


    @ExcelProperty("原价")
    private String originPrice;

    @ExcelProperty("数量")
    private String quantity;

    @ExcelProperty("采购价")
    private String buyPrice;

    @ExcelProperty("供应商")
    private String supplierName;

    @ExcelProperty("付款方")
    private String payerName;

    @ExcelProperty("项目部/实验部")
    private String purchaseManagerName;

    @ExcelProperty("销售人员")
    private String salesName;

    @ExcelProperty("客户")
    private String customerName;
}

