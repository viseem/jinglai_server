package cn.iocoder.yudao.module.jl.controller.admin.contractinvoicelog.vo;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户 Excel 导入 VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class ContractInvoiceLogImportVO {


    @ExcelProperty("开票日期")
    private String date;

    @ExcelProperty("客户")
    private String customerMark;

    @ExcelProperty("电话")
    private String phone;

    @ExcelProperty("开票单位")
    private String title;

    @ExcelProperty("税号")
    private String taxNumber;

    @ExcelProperty("销售员")
    private String salesName;

    @ExcelProperty("发票金额")
    private String price;

    @ExcelProperty("发票冲红")
    private String redPrice;

    @ExcelProperty("发票类型")
    private String headType;

    @ExcelProperty("发票号")
    private String number;

    @ExcelProperty("开票公司")
    private String billCompany;

    @ExcelProperty("是否到账")
    private String status;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("系统内编号(勿动)")
    private String id;


/*    @ExcelProperty(value = "用户性别", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.USER_SEX)
    private Integer sex;

    @ExcelProperty(value = "账号状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;*/
}
