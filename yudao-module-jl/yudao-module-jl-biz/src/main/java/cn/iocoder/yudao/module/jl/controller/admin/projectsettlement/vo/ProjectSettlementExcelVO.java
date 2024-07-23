package cn.iocoder.yudao.module.jl.controller.admin.projectsettlement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigDecimal;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目结算 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProjectSettlementExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("结算金额")
    private BigDecimal paidAmount;

    @ExcelProperty("提醒日期")
    private LocalDateTime reminderDate;

    @ExcelProperty("应收金额")
    private BigDecimal amount;

    @ExcelProperty("备注")
    private String mark;

}
