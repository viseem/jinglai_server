package cn.iocoder.yudao.module.jl.controller.admin.contractinvoicelog.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.*;
import java.util.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 合同发票记录 Excel VO
 *
 * @author 惟象科技
 */
@Data
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER,verticalAlignment= VerticalAlignmentEnum.CENTER)//内容样式
public class ContractInvoiceLogExcelVO {


    @ExcelProperty("开票日期")
    private LocalDate date;

    @ExcelProperty("合同编号")
    private String contractSn;

    @ExcelProperty("客户")
    private String customerName;

    @ExcelProperty("电话")
    private String phone;

    @ExcelProperty("税号")
    private String taxerNumber;

    @ExcelProperty("销售")
    private String salesName;

    @ExcelProperty("开票金额")
    private String price;

    @ExcelProperty("发票类型")
    private String headType;

    @ExcelProperty("发票号")
    private String number;

    @ExcelProperty("发票冲红")
    private String redPrice;

    @ExcelProperty("开票公司")
    private String billCompany;

    @ExcelProperty(value = "发票状态", converter = DictConvert.class)
    @DictFormat("CONTRACT_INVOICE_STATUS") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private String status;

    @ExcelProperty("备注")
    private String mark;
}
