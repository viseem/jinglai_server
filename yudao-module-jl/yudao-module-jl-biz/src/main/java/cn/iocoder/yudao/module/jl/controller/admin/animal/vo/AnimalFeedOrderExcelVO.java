package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

/**
 * 动物饲养申请单 Excel VO
 *
 * @author 惟象科技
 */
@Data
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER,verticalAlignment= VerticalAlignmentEnum.CENTER)//内容样式
public class AnimalFeedOrderExcelVO {


    @ExcelProperty("客户")
    private String customerName;

    @ExcelProperty("饲养单名字")
    private String name;


/*
    @ExcelProperty("客户对接人")
    private String serviceName;*/

    @ExcelProperty("品种品系")
    private String breedAndStrain;

    @ExcelProperty("饲养位置")
    private String location;

/*    @ExcelProperty("位置代码")
    private String locationCode;*/

    @ExcelProperty("饲养方式")
    private String feedType;

    @ExcelProperty("饲养开始日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDate startDate;

    @ExcelProperty("饲养结束日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDate endDate;

    @ExcelProperty("饲养笼数")
    private Integer cageQuantity;

    @ExcelProperty("饲养只数")
    private Integer quantity;

    @ExcelProperty("现有笼数")
    private Integer currentCageQuantity;

    @ExcelProperty("现有只数")
    private Integer currentQuantity;

    @ExcelProperty("收费方式")
    @DictFormat(DictTypeConstants.FEED_BILL_RULES)
    private String billRules;

    @ExcelProperty("收费单价")
    private String unitFee;

    @ExcelProperty("收费总额")
    private String amount;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("编号")
    private String code;

/*    @ExcelProperty("状态")
    private String stage;*/

}
