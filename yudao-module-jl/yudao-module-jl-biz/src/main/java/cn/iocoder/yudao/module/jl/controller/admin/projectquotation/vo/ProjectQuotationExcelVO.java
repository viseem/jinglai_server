package cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;
import lombok.Data;

/**
 * 项目报价 Excel VO
 *
 * @author 惟象科技
 */
@Data

@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER,verticalAlignment= VerticalAlignmentEnum.CENTER)//内容样式
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

}
