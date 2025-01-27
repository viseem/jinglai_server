package cn.iocoder.yudao.module.jl.controller.admin.laboratory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 实验名目的收费项 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class CategoryChargeitemExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("收费项 id")
    private Long chargeItemId;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("收费的标准/规则")
    private String feeStandard;

    @ExcelProperty("单价")
    private String unitFee;

    @ExcelProperty("数量")
    private Integer quantity;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("实验名目 id")
    private Long categoryId;

    @ExcelProperty("单量")
    private Integer unitAmount;

}
