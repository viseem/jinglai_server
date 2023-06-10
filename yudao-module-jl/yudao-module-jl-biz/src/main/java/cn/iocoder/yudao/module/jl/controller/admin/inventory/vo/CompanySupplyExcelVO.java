package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 公司实验物资库存 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class CompanySupplyExcelVO {

    @ExcelProperty("岗位ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("物资 id")
    private Long supplyId;

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

    @ExcelProperty("有效期")
    private String validDate;

}
