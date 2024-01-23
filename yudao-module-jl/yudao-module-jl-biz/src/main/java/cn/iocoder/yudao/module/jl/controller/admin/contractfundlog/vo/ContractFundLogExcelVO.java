package cn.iocoder.yudao.module.jl.controller.admin.contractfundlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 合同收款记录 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ContractFundLogExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("金额")
    private Long price;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("凭证地址")
    private String fileUrl;

    @ExcelProperty("凭证")
    private String fileName;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("支付日期")
    private LocalDateTime paidTime;

    @ExcelProperty("付款方")
    private String payer;

    @ExcelProperty("收款方")
    private String payee;

    @ExcelProperty("合同id")
    private Long contractId;

    @ExcelProperty("项目id")
    private Long projectId;

    @ExcelProperty("客户id")
    private Long customerId;

}
