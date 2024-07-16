package cn.iocoder.yudao.module.jl.controller.admin.customerchangelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 客户变更日志 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class CustomerChangeLogExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("客户id")
    private Long customerId;

    @ExcelProperty("类型")
    private String type;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("转给谁")
    private Long toOwnerId;

    @ExcelProperty("来自谁")
    private Long fromOwnerId;

}
