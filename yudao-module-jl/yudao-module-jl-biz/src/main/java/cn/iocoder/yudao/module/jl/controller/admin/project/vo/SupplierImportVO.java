package cn.iocoder.yudao.module.jl.controller.admin.project.vo;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import cn.iocoder.yudao.module.system.enums.DictTypeConstants;
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
public class SupplierImportVO {

    @ExcelProperty("供应商名称")
    private String name;

    @ExcelProperty("联系人")
    private String contactName;

    @ExcelProperty("联系方式")
    private Long contactPhone;

    @ExcelProperty("渠道类型")
    private String channelTypeStr;

    @ExcelProperty("开票类型")
    private String billWayStr;

    @ExcelProperty("结算周期")
    private String paymentCycleStr;

    @ExcelProperty("银行卡号")
    private String bankAccount;

    @ExcelProperty("银行支行")
    private String subBranch;

    @ExcelProperty("职位")
    private String contactLevel;

    @ExcelProperty("服务折扣")
    private String discount;

    @ExcelProperty("品类")
    private String product;

    @ExcelProperty("备注")
    private String mark;


/*    @ExcelProperty(value = "用户性别", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.USER_SEX)
    private Integer sex;

    @ExcelProperty(value = "账号状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;*/
}
