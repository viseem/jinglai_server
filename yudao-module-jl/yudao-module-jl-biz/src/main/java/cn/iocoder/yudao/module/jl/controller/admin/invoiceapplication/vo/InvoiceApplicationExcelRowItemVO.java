package cn.iocoder.yudao.module.jl.controller.admin.invoiceapplication.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@ContentStyle(wrapped = BooleanEnum.TRUE,horizontalAlignment = HorizontalAlignmentEnum.CENTER,verticalAlignment= VerticalAlignmentEnum.CENTER)
public class InvoiceApplicationExcelRowItemVO {

    @ExcelProperty("名称")
    private String col1 = "";

    @ExcelProperty( "品牌")
    private String col2 = "";

    @ExcelProperty( "货号")
    private String col3 = "";

    @ExcelProperty( "规格")
    private String col4 = "";

    @ExcelProperty( "单位")
    private String col5 = "";

    @ExcelProperty( "单价(元)")
    private String col6 = "";

    @ExcelProperty( "数量")
    private String col7 = "";

    @ExcelProperty( "金额")
    private String col8 = "";

}
