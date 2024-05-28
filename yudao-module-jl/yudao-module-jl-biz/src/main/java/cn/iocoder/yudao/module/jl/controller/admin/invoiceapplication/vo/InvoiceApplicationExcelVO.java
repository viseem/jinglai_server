package cn.iocoder.yudao.module.jl.controller.admin.invoiceapplication.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 开票申请 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class InvoiceApplicationExcelVO {

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("品牌")
    private String brand;

    @ExcelProperty("货号")
    private String productCode;

    @ExcelProperty("规格")
    private String spec;

    @ExcelProperty("单价(元)")
    private String price;

    @ExcelProperty("数量")
    private String quantity;

    @ExcelProperty("金额")
    private String amount;
}
