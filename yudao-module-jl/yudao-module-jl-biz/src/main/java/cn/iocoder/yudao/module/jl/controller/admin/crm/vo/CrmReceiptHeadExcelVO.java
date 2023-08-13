package cn.iocoder.yudao.module.jl.controller.admin.crm.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 客户发票抬头 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class CrmReceiptHeadExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("开票类型")
    private String type;

    @ExcelProperty("开票抬头")
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

    @ExcelProperty("客户id")
    private Long customerId;

    @ExcelProperty("备注")
    private String mark;

}
