package cn.iocoder.yudao.module.jl.controller.admin.contractfundlog.vo;
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
public class ContractFundLogImportVO {

    @ExcelProperty("款项名称")
    private String name;

    @ExcelProperty("回款日期")
    private String receiveDate;

    @ExcelProperty("金额")
    private String price;

    @ExcelProperty("转账单位")
    private String payer;

    @ExcelProperty("客户")
    private String customerMark;

    @ExcelProperty("收款账户")
    private String payee;

    @ExcelProperty("销售人员")
    private String salesName;

    @ExcelProperty("备注")
    private String mark;


/*    @ExcelProperty(value = "用户性别", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.USER_SEX)
    private Integer sex;

    @ExcelProperty(value = "账号状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;*/
}
