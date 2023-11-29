package cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo;

import com.alibaba.excel.annotation.write.style.ContentLoopMerge;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

import javax.validation.constraints.NotNull;

/**
 * 项目报价 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProjectQuotationExcelVO {

/*    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("版本")
    private String code;*/

/*    @ExcelProperty("备注")
    private String mark;*/

    @ExcelProperty("品目")
    private String projectCategoryName;

    @ExcelProperty("项目名称")
    private String name;

    @ExcelProperty("单位")
    private String feeStandard;

    @ExcelProperty("规格")
    private Integer unitAmount;

    @ExcelProperty("数量")
    private Integer quantity;

    @ExcelProperty("单价")
    private Integer unitFee;

    @ExcelProperty("成本价")
    private Integer buyPrice;

    @ExcelProperty("备注")
    private String mark;

    @Schema(description = "物品来源")
    private String source;

}
