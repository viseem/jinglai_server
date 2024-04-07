package cn.iocoder.yudao.module.jl.controller.admin.crm.vo;

import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;


/**
 * 销售线索 Excel VO
 *
 * @author 惟象科技
 */
@Data
//excel内容居中
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER,verticalAlignment= VerticalAlignmentEnum.CENTER)//内容样式
public class SalesleadExcelVO {


    @ExcelProperty("咨询时间")
    private LocalDate createTime;

    @ExcelProperty("咨询编号")
    private String id;

    @ExcelProperty("销售")
    private String salesName;

    @ExcelProperty("客户姓名")
    private String customerName;

    @ExcelProperty("客户单位")
    private String customerInstitution;

    @ExcelProperty("客户联系方式")
    private String customerPhone;

    @ExcelProperty("客户类型")
    private String customerType;

    @ExcelProperty("项目负责人")
    private String managerName;

    @ExcelProperty(value = "项目类型", converter = DictConvert.class)
    @DictFormat("business_type") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private String businessType;

    @ExcelProperty("是否报价")
    private String isQuotation;

    @ExcelProperty("报价时间")
    private LocalDate quotationUpdateTime;

    @ExcelProperty("项目内容")
    private String requirement;

    @ExcelProperty("项目情况")
    private String projectStatus;

    @ExcelProperty("成本价")
    private String costPrice;

    @ExcelProperty("供应商")
    private String supplier;

    @ExcelProperty("终端价")
    private String quotation;

    @ExcelProperty("最近跟进时间")
    private String lastFollowTime;

    @ExcelProperty("最近跟进内容")
    private String lastFollowContent;


}
