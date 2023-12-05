package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 动物饲养申请单 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class AnimalFeedOrderExcelVO {

    @ExcelProperty("饲养单名字")
    private String name;

    @ExcelProperty("编号")
    private String code;

    @ExcelProperty("客户")
    private String customerName;

    @ExcelProperty("客户对接人")
    private String serviceName;

    @ExcelProperty("品系品种")
    private String breed;

    @ExcelProperty("饲养室")
    private String locationName;

    @ExcelProperty("饲养方式")
    private String feedType;

    @ExcelProperty("饲养开始日期")
    private LocalDateTime startDate;

    @ExcelProperty("饲养结束日期")
    private LocalDateTime endDate;

    @ExcelProperty("饲养笼数")
    private Integer cageQuantity;

    @ExcelProperty("饲养只数")
    private Integer quantity;

    @ExcelProperty("现有笼数")
    private Integer latestCageQuantity;

    @ExcelProperty("现有只数")
    private Integer latestQuantity;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("状态")
    private String stage;

}
