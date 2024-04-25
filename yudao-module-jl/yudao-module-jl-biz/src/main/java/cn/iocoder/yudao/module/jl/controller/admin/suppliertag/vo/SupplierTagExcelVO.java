package cn.iocoder.yudao.module.jl.controller.admin.suppliertag.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 供应商标签 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class SupplierTagExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("备注")
    private String mark;

}
