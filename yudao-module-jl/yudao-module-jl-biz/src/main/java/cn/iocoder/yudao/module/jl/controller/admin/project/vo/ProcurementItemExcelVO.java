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

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("采购单id")
    private Long procurementId;

    @ExcelProperty("项目物资 id")
    private Long projectSupplyId;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("采购规则/单位")
    private String feeStandard;

    @ExcelProperty("单价")
    private String unitFee;

    @ExcelProperty("单量")
    private Integer unitAmount;

    @ExcelProperty("采购数量")
    private Integer quantity;

    @ExcelProperty("供货商id")
    private Long supplierId;

    @ExcelProperty("原价")
    private Integer buyPrice;

    @ExcelProperty("销售价")
    private Integer salePrice;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("有效期")
    private String validDate;

    @ExcelProperty("品牌")
    private String brand;

    @ExcelProperty("目录号")
    private String catalogNumber;

    @ExcelProperty("货期")
    private String deliveryDate;

    @ExcelProperty("状态:等待采购信息、等待打款、等待采购、等待签收、等待入库")
    private String status;

}
