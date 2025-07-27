package cn.iocoder.yudao.module.jl.controller.admin.product.vo;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户 Excel 导入 VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class ProductImportVO {

    @ExcelProperty("服务项目")
    private String name;

    @ExcelProperty("规格/单位")
    private String spec;

    @ExcelProperty("服务单价")
    private String standardPrice;

    @ExcelProperty("备注")
    private String mark;

}
