package cn.iocoder.yudao.module.jl.controller.admin.crm.vo;
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
public class InstitutionImportTaxNumberVO {

    @ExcelProperty("客户")
    private String contact;

    @ExcelProperty("开票单位")
    private String name;

    @ExcelProperty("税号")
    private String billCode;

    @ExcelProperty("类型")
    private String type;

}
