package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 取货单申请明细 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class SupplyPickupItemExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("取货单申请id")
    private Long supplyPickupId;

    @ExcelProperty("项目物资 id")
    private Long projectSupplyId;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("规则/单位")
    private String feeStandard;

    @ExcelProperty("单价")
    private String unitFee;

    @ExcelProperty("单量")
    private Integer unitAmount;

    @ExcelProperty("数量")
    private Integer quantity;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("有效期")
    private String validDate;

    @ExcelProperty("存储温度")
    private String temperature;

    @ExcelProperty("状态")
    private String status;

}
