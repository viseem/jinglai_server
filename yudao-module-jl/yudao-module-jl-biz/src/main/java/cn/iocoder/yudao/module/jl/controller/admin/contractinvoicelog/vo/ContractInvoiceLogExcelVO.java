package cn.iocoder.yudao.module.jl.controller.admin.contractinvoicelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
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
public class ContractInvoiceLogExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("发票申请编号")
    private String code;

    @ExcelProperty("客户id")
    private Long customerId;

    @ExcelProperty("合同id")
    private Long contractId;

    @ExcelProperty("开票金额")
    private BigDecimal price;

    @ExcelProperty("开票日期")
    private LocalDateTime date;

    @ExcelProperty("开票类型：增值税专用发票")
    private String type;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("发票号码")
    private String number;

    @ExcelProperty("实际开票日期")
    private LocalDateTime actualDate;

    @ExcelProperty("物流单号")
    private String shipmentNumber;

    @ExcelProperty("开发方式：电子、纸质")
    private String way;

    @ExcelProperty("发票抬头id")
    private Long receiptHeadId;

    @ExcelProperty("发票抬头")
    private String title;

    @ExcelProperty("纳税人识别号")
    private String taxerNumber;

    @ExcelProperty("开户行")
    private String bankName;

    @ExcelProperty("开户账号")
    private String bankAccount;

    @ExcelProperty("开票地址")
    private String address;

    @ExcelProperty("联系电话")
    private String phone;

    @ExcelProperty("抬头类型")
    private String headType;

    @ExcelProperty("邮寄联系人")
    private String sendContact;

    @ExcelProperty("联系电话")
    private String sendPhone;

    @ExcelProperty("详细地址")
    private String sendAddress;

    @ExcelProperty("邮寄省份")
    private String sendProvince;

    @ExcelProperty("邮寄市")
    private String sendCity;

    @ExcelProperty("邮寄区")
    private String sendArea;

    @ExcelProperty("负责人")
    private Long manager;

    @ExcelProperty("实际已收")
    private Long receivedPrice;

    @ExcelProperty("状态")
    private String status;

}
