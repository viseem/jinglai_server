package cn.iocoder.yudao.module.jl.controller.admin.financepayment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 财务打款 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class FinancePaymentExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("申请单")
    private Long refId;

    @ExcelProperty("类型")
    private String type;

    @ExcelProperty("说明")
    private String mark;

    @ExcelProperty("审批人")
    private Long auditUserId;

    @ExcelProperty("审批说明")
    private String auditMark;

    @ExcelProperty("审批状态")
    private String auditStatus;

    @ExcelProperty("金额")
    private BigDecimal price;

    @ExcelProperty("凭据")
    private String proofUrl;

    @ExcelProperty("凭据名称")
    private String proofName;

    @ExcelProperty("打款凭证")
    private String paymentUrl;

    @ExcelProperty("打款凭证名称")
    private String paymentName;

}
