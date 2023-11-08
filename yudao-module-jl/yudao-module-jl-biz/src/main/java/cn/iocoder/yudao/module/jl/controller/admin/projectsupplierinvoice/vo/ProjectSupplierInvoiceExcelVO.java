package cn.iocoder.yudao.module.jl.controller.admin.projectsupplierinvoice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 采购供应商发票 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProjectSupplierInvoiceExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("金额")
    private Long price;

    @ExcelProperty("凭证")
    private String fileName;

    @ExcelProperty("凭证")
    private String fileUrl;

    @ExcelProperty("项目id")
    private Long projectId;

    @ExcelProperty("供应商id")
    private Long supplierId;

    @ExcelProperty("开票日期")
    private LocalDateTime date;

    @ExcelProperty("备注")
    private String mark;

}
