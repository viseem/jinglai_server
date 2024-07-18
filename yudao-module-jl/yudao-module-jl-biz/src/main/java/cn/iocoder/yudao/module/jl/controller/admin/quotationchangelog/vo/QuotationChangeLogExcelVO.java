package cn.iocoder.yudao.module.jl.controller.admin.quotationchangelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 报价变更日志 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class QuotationChangeLogExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("项目id")
    private Long projectId;

    @ExcelProperty("报价id")
    private Long qutationId;

    @ExcelProperty("客户id")
    private Long customerId;

    @ExcelProperty("变更原因")
    private String mark;

    @ExcelProperty("变更方案")
    private String planText;

    @ExcelProperty("变更明细")
    private String jsonLogs;

}
