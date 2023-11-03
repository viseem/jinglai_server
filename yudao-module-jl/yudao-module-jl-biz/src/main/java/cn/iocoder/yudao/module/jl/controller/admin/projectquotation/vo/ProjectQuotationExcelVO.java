package cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目报价 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProjectQuotationExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("版本号")
    private String code;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("方案")
    private String planText;

    @ExcelProperty("项目id")
    private Long projectId;

    @ExcelProperty("客户id")
    private Long customerId;

}
