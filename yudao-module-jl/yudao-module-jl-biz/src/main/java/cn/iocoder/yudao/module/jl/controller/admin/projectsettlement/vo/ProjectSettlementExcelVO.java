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

    @ExcelProperty("品目")
    private String projectCategoryName;

    @ExcelProperty("项目名称")
    private String name;

    @ExcelProperty("规格/单位")
    private String spec;

    @ExcelProperty("数量")
    private Integer quantity;

    @ExcelProperty("单价")
    private Integer unitFee;

    @ExcelProperty("金额")
    private String priceAmount;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("周期")
    private String projectCategoryCycle;

}
