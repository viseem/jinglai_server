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
public class InstitutionImportVO {

    @ExcelProperty("企业名称")
    private String name;

    @ExcelProperty("法定代表人")
    private String legalRepresentative;

    @ExcelProperty("注册资本")
    private String registerCapital;

    @ExcelProperty("成立日期")
    private String establishDate;

    @ExcelProperty("统一社会信用代码")
    private String creditCode;

    @ExcelProperty("企业注册地址")
    private String registerAddress;

    @ExcelProperty("电话")
    private String phone;

    @ExcelProperty("邮箱")
    private String email;

    @ExcelProperty("所属省份")
    private String province;

    @ExcelProperty("所属区县")
    private String area;

    @ExcelProperty("纳税人识别号")
    private String billCode;

    @ExcelProperty("官网网址")
    private String website;

    @ExcelProperty("企业简介")
    private String profile;

    @ExcelProperty("经营范围")
    private String businessScope;

    @ExcelProperty("类型")
    private String type;

}
