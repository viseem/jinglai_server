package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目采购单打款 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProcurementPaymentExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("采购单id")
    private Long procurementId;

    @ExcelProperty("打款时间")
    private String paymentDate;

    @ExcelProperty("打款金额")
    private String amount;

    @ExcelProperty("供货商 id")
    private Long supplierId;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("凭证")
    private String proofName;

    @ExcelProperty("凭证地址")
    private String proofUrl;

}
