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

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("饲养单名字")
    private String name;

    @ExcelProperty("编号")
    private String code;

    @ExcelProperty("品系品种")
    private String breed;

    @ExcelProperty("周龄体重")
    private String age;

    @ExcelProperty("数量")
    private Integer quantity;

    @ExcelProperty("雌")
    private Integer femaleCount;

    @ExcelProperty("雄")
    private Integer maleCount;

    @ExcelProperty("供应商id")
    private Long supplierId;

    @ExcelProperty("供应商名字")
    private String supplierName;

    @ExcelProperty("合格证号")
    private String certificateNumber;

    @ExcelProperty("许可证号")
    private String licenseNumber;

    @ExcelProperty("开始日期")
    private LocalDateTime startDate;

    @ExcelProperty("结束日期")
    private LocalDateTime endDate;

    @ExcelProperty("有无传染性等实验")
    private Boolean hasDanger;

    @ExcelProperty("饲养类型：普通饲养")
    private String feedType;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("项目id")
    private Long projectId;

    @ExcelProperty("客户id")
    private Long customerId;

    @ExcelProperty("状态")
    private String stage;

    @ExcelProperty("回复")
    private String reply;

}
