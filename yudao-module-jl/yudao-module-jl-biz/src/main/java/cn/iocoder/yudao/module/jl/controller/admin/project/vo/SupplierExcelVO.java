package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目采购单物流信息 Excel VO
 *
 * @author 惟象科技
 */
@Data
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER,verticalAlignment= VerticalAlignmentEnum.CENTER)//内容样式
public class SupplierExcelVO {

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

}
