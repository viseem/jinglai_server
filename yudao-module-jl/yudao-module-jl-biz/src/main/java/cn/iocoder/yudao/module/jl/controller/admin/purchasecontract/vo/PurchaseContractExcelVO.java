package cn.iocoder.yudao.module.jl.controller.admin.purchasecontract.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 购销合同 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class PurchaseContractExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("供应商id")
    private Integer supplierId;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("总价")
    private BigDecimal amount;

}
